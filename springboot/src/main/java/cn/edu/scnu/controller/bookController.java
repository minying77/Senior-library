package cn.edu.scnu.controller;

import cn.edu.scnu.Utils.TokenUtils;
import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.BookDTO;
import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.service.IBookService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/book")
public class bookController {

    @Autowired
    IBookService bookService;

    private final static String BASE_FILE_PATH=System.getProperty("user.dir") + "/files/";


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String ALL_BOOKS_CACHE_KEY = "ALL_BOOKS";

    /**
     * 查询所有图书
     * @return list
     */
    @GetMapping("/list")
    public Result list(){
        String cacheKey = ALL_BOOKS_CACHE_KEY;
        String json = stringRedisTemplate.opsForValue().get(cacheKey);
        List<Book> books;
        if (StrUtil.isBlank(json)) {
            books = bookService.list();
            setCache(cacheKey, JSONUtil.toJsonStr(books));
        } else {
            books = JSONUtil.toBean(json, new TypeReference<List<Book>>() {}, true);
        }
        return Result.success(books);
    }

    /**
     * 分页查询图书
     * @return list
     */
    @PostMapping("/page")
    public Result page(@RequestBody BookPageRequest bookPageRequest){
        Page<BookDTO> list= bookService.page(bookPageRequest);
        return Result.success(list);
    }

    /**
     * 根据id查询图书
     * @param id
     * @return category
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        String cacheKey = "BOOK_" + id;
        String json = stringRedisTemplate.opsForValue().get(cacheKey);
        Book book;
        if (StrUtil.isBlank(json)) {
            book = bookService.getById(id);
            log.info(book.toString());
            if (book != null) {
                setCache(cacheKey, JSONUtil.toJsonStr(book));
            }
        } else {
            book = JSONUtil.toBean(json, Book.class);
        }
        log.info(book.toString());
        return book != null ? Result.success(book) : Result.error("图书不存在");

    }

    /**
     * 新增图书
     * @param bookDTO 分类信息
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody BookDTO bookDTO){

        bookService.add(bookDTO);
        flushRedis(ALL_BOOKS_CACHE_KEY);

        return Result.success();
    }



    /**
     * 修改图书
     * @param bookDTO
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody BookDTO bookDTO){
        bookService.update(bookDTO);
        flushRedis(ALL_BOOKS_CACHE_KEY);
        return Result.success();
    }

    /**
     * 删除图书
     * @param id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.deleteById(id);
        flushRedis(ALL_BOOKS_CACHE_KEY);
        return Result.success();
    }

    @PostMapping("/file/upload")
    public Result uploadFile(HttpServletRequest request, MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        if(StrUtil.isBlank(originalFilename)){
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;
        try{
            FileUtil.mkParentDirs(filePath);
            file.transferTo(FileUtil.file(filePath));
           // Admin currentAdmin = TokenUtils.getCurrentAdmin();
            //request.getCookies();
            //String token = TokenUtils.genToken(currentAdmin.getId().toString(), currentAdmin.getPassword());
            String url = "http://localhost:8081/api/book/file/download/"+flag;
            if(originalFilename.endsWith("png") || originalFilename.endsWith("jpg")){
                url += "?play=1";
            }
            return Result.success(url);

        }catch (Exception e){
            log.info("文件上传失败",e);
        }
        return Result.error("文件上传失败");
    }


    @GetMapping("/file/download/{flag}")
    private void download(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response){
        OutputStream os;
        List<String> filesName = FileUtil.listFileNames(BASE_FILE_PATH);
        log.info(flag);
        log.info(filesName.toString());
        String fileName = filesName.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        log.info(fileName);
        try{
            if(StrUtil.isNotEmpty(fileName)){
                String realName = fileName.substring(fileName.indexOf("_") + 1 );
                if("1".equals(play)){
                    response.addHeader("Content-Disposition", "inline;filename="+ URLEncoder.encode(realName, "UTF-8"));
                }else {
                    response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);

                os = response.getOutputStream();
                os.write(bytes);
                log.info(os.toString());
                os.flush();
                os.close();
            }
        }catch (Exception e){
            log.error("文件下载失败",e);
        }
    }


    // 根据分类 ID 查询图书
    @PostMapping("/listByCategory/{categoryId}")
    public Result listByCategory( @PathVariable Integer categoryId, @RequestBody BaseRequest baseRequest) {
        String cacheKey = "BOOKS_BY_CATEGORY_" + categoryId;
        String json = stringRedisTemplate.opsForValue().get(cacheKey);
        List<Book> books;
        if (StrUtil.isBlank(json)) {
            log.info("缓存中分类id为{}的图书为空，从数据库获取...", categoryId);
            books = bookService.listByCategory(categoryId);
            setCache(cacheKey, JSONUtil.toJsonStr(books));
        } else {
            log.info("缓存中获取到分类id为{}的图书，data:{}", categoryId,json);
            books = JSONUtil.toBean(json, new TypeReference<List<Book>>() {}, true);
        }
        return Result.success(listToPage(books, baseRequest.getPageNum(), baseRequest.getPageSize()));
    }

    private Page<Book> listToPage(List list, int pageNum, int pageSize){
        List pageList = new ArrayList<>();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        Page<Book> page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }

    //设置缓存
    private void setCache(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    //删除缓存
    private void flushRedis(String key){
        stringRedisTemplate.delete(key);
    }

}
