package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.CategoryPageDTO;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.controller.request.CategoryPageRequest;
import cn.edu.scnu.entity.Book;
import cn.edu.scnu.entity.Category;
import cn.edu.scnu.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class bookController {

    @Autowired
    IBookService bookService;

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
    @GetMapping("/page")
    public Result page(@RequestBody BookPageRequest bookPageRequest){
        List<Book> list= bookService.page(bookPageRequest);
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
     * @param book 分类信息
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Book book){

        bookService.add(book);
        return Result.success();
    }



    /**
     * 修改图书
     * @param book
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Book book){
        bookService.update(book);
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



}
