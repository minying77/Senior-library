package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.dto.RegisterDTO;
import cn.edu.scnu.controller.dto.UserDTO;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.controller.request.RegisterRequest;
import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NavigableMap;

import static cn.edu.scnu.controller.adminController.securePass;


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
        Page<User> userList= userService.page(userPageRequest);  //page(当前页码, 每页记录数)
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


//    @GetMapping("/{name}")
//    public Result getUserByName(@PathVariable String name){
//        User user = userService.getUserByName(name);
//        return Result.success(user);
//    }


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


    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest,  HttpSession session){
        loginRequest.setPassword(securePass(loginRequest.getPassword()));
        LoginDTO userDTO = userService.login(loginRequest);

        session.setAttribute("loginDetail", userDTO);
        return Result.success(userDTO);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterRequest registerRequest, HttpSession session){
        registerRequest.setPassword(securePass(registerRequest.getPassword()));
        RegisterDTO userDTO=userService.register(registerRequest);

        session.setAttribute("registerDetail",userDTO);
        return Result.success(userDTO);
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

    @PutMapping("/addAccount")
    public Result addAccount(@RequestBody UserDTO user){
        userService.addAccount(user);
        return Result.success();
    }
}
