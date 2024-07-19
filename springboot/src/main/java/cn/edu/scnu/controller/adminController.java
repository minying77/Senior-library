package cn.edu.scnu.controller;

import cn.edu.scnu.common.Result;
import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.service.IAdminService;
import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class adminController {

    private static final String PASS_SALT = "Senior";

    @Autowired
    IAdminService adminService;

    /**
     * 查询所有管理者
     * @return adminList
     */
    @GetMapping("/list")
    public Result list(){
        List<Admin> userList =  adminService.list();
        return Result.success(userList);
    }

    /**
     * 分页查询管理者
     * @return 该页用户list
     */
    @PostMapping("/page")
    public Result page(@RequestBody AdminPageRequest adminPageRequest){
        List<Admin> userList= adminService.page(adminPageRequest);  //page(当前页码, 每页记录数)
        return Result.success(userList);
    }

    /**
     * 根据id查询管理者
     * @param id
     * @return admin
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable Integer id){
        Admin admin = adminService.getAdminById(id);
        return Result.success(admin);
    }

    /**
     * 新增管理者 或者 注册管理者
     * @param admin 管理者信息
     * @return Result
     */
    @PostMapping("/add")
    public Result addUser(@RequestBody Admin admin){
        //admin密码加密存储
        admin.setPassword(securePass(admin.getPassword()));
        adminService.addAdmin(admin);
        return Result.success();
    }

    /**
     * 修改管理者信息
     * @param admin
     * @return Result
     */
    @PutMapping("/update")
    public Result updateUser(@RequestBody  Admin admin){
        adminService.updateAdminById(admin);
        return Result.success();
    }

    /**
     * 删除管理者
     * @param id 管理者id
     * @return Result
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }



    /**
     * 登录管理者
     * @param loginRequest 封装请求参数
     * 登录流程 - 基于session的身份认证：（session存储在服务器中）
     *  1、前端调用登录接口，往接口里传入账号，密码
     *  2、根据账号判断是否有这个用户，如果有则继续判断密码是否正确
     *  3、验证成功后，服务器生成一个sessionId，并存储在redis中
     *  4、前端将其存入cookie中，并在下次请求时携带上，用于身份认证
     * @return Result
     */

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest,  HttpSession session){
        loginRequest.setPassword(securePass(loginRequest.getPassword()));
        LoginDTO adminDTO = adminService.login(loginRequest);

        session.setAttribute("loginDetail", adminDTO);
        return Result.success(adminDTO);
    }

    /**
     * 封装加密方法
     * @param password
     * @return
     */
    public static String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }

}
