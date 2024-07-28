package cn.edu.scnu.service;

import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.dto.RegisterDTO;
import cn.edu.scnu.controller.dto.UserDTO;
import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.controller.request.RegisterRequest;
import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IUserService {

    List<User> list();

    Page<User> page(UserPageRequest userPageRequest);

    void addUser(User user);

    User getUserById(Integer id);

    User getUserByName(String name);

    void updateUserById(User user);

    void deleteById(Integer id);

    void addAccount(UserDTO user);

    LoginDTO login(LoginRequest loginRequest);

    RegisterDTO register(RegisterRequest registerRequest);

}
