package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.BorrowPageDTO;
import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.BorrowPageRequest;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.service.IBorrowService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @PostMapping("/page")
    public Result page(@RequestBody BorrowPageRequest borrowPageRequest){
        Page<BorrowPageDTO> list= borrowService.page(borrowPageRequest);
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

    /**
     *  修改借阅状态
     * @return
     */
    @PutMapping("/changeStatusYes/{id}")
    public Result changeStatusYes(@PathVariable Integer id){
        borrowService.changeStatus(id, "yes");
        return Result.success();
    }

    /**
     *  修改借阅状态
     * @return
     */
    @PutMapping("/changeStatusNo/{id}")
    public Result changeStatusNo(@PathVariable Integer id){
        borrowService.changeStatus(id, "no");
        return Result.success();
    }


    //根据用户id查询借阅记录
    @PostMapping("/listByUser/{userId}")
    public Result listByUser(@PathVariable Integer userId, @RequestBody BaseRequest baseRequest) {
        Page<BorrowPageDTO> list = borrowService.listByUser(userId, baseRequest);
        return Result.success(list);
    }

    /**
     * 归还图书
     * @param id 借书记录ID
     * @return Result
     */
    @PostMapping("/saveReturn/{id}")
    public Result saveReturn(@PathVariable Integer id){
        borrowService.returnBook(id);
        return Result.success();
    }
//    /**
//     * 删除归还记录
//     * @param id 借书记录ID
//     * @return Result
//     */
//    @PostMapping("/deleteReturn/{id}")
//    public Result deleteReturn(@PathVariable Integer id){
//        borrowService.deleteReturnById(id);
//        return Result.success();
//    }
    /**
     * 首页图表统计
     * @timeRange week month month2 month3
     * @return Result
     */
    @GetMapping("/lineCharts/{timeRange}")
    public Result lineCharts(@PathVariable String timeRange){
        return Result.success(borrowService.getCountByTimeRange(timeRange));
    }

}
