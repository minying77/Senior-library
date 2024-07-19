package cn.edu.scnu.service;

import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface IUserService {

    List<User> list();

    Page<User> page(UserPageRequest userPageRequest);

    void addUser(User user);

    User getUserById(Integer id);

    void updateUserById(User user);

    void deleteById(Integer id);
}
