package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    IUserService userService;

    /**
     * 查询所有用户
     * @return userList
     */
    @GetMapping("/list")
    public Result list(){
       List<User> userList =  userService.list();
       return Result.success(userList);
    }

    /**
     * 分页查询用户
     * @return 该页用户list
     */
    @PostMapping("/page")
    public Result page(@RequestBody  UserPageRequest userPageRequest){
        List<User> userList= userService.page(userPageRequest);  //page(当前页码, 每页记录数)
        return Result.success(userList);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return user
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    /**
     * 新增用户
     * @param user 用户信息
     * @return Result
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody User user){
        userService.addUser(user);
        return Result.success();
    }

    /**
     * 修改用户信息（注意卡号不可改动）
     * @param user
     * @return Result
     */
    @PutMapping("/update")
    public Result updateUser(@RequestBody  User user){
        userService.updateUserById(user);
        return Result.success();
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }
}
