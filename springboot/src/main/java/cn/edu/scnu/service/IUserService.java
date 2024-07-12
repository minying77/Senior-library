package cn.edu.scnu.service;

import cn.edu.scnu.controller.request.BaseRequest;
import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;

import java.util.List;

public interface IUserService {

    List<User> list();

    List<User> page(UserPageRequest userPageRequest);

    void addUser(User user);

    User getUserById(Integer id);

    void updateUserById(User user);

    void deleteById(Integer id);
}
