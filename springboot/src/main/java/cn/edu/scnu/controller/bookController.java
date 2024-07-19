package cn.edu.scnu.controller;

import cn.edu.scnu.Utils.TokenUtils;
import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.BookDTO;
import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.service.IBookService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 查询所有图书
     * @return list
     */
    @GetMapping("/list")
    public Result list(){
        List<Book> list =  bookService.list();
        return Result.success(list);
    }

    /**
     * 分页查询图书
     * @return list
     */
    @PostMapping("/page")
    public Result page(@RequestBody BookPageRequest bookPageRequest){
        IPage<BookDTO> list= bookService.page(bookPageRequest);
        return Result.success(list);
    }

    /**
     * 根据id查询图书
     * @param id
     * @return category
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Book book = bookService.getById(id);
        return Result.success(book);
    }

    /**
     * 新增图书
     * @param bookDTO 分类信息
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody BookDTO bookDTO){

        bookService.add(bookDTO);
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


}
