package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.request.BookPageRequest;
import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.service.IBookService;
import cn.edu.scnu.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/borrow")
public class borrowController {

    @Autowired
    IBorrowService borrowService;

    /**
     * 查询所有借阅记录
     * @return list
     */
    @GetMapping("/list")
    public Result list(){
        List<Borrow> list =  borrowService.list();
        return Result.success(list);
    }

    /**
     * 分页查询
     * @return list
     */
    @GetMapping("/page")
    public Result page(@RequestBody BorrowPageRequest borrowPageRequest){
        List<Borrow> list= borrowService.page(borrowPageRequest);
        return Result.success(list);
    }

    /**
     * 根据id查询
     * @param id
     * @return borrow
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Borrow borrow = borrowService.getById(id);
        return Result.success(borrow);
    }

    /**
     * 新增借阅
     * @param borrow
     * @return Result
     */
    @PostMapping("/add")
    public Result add(@RequestBody Borrow borrow){
        borrowService.add(borrow);
        return Result.success();
    }



    /**
     * 修改
     * @param borrow
     * @return Result
     */
    @PutMapping("/update")
    public Result update(@RequestBody Borrow borrow){
        borrowService.update(borrow);
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }

}
