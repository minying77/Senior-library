package com.example.springboot.service;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;

import java.util.List;

public interface IUserService {
    List<User> list();//查询所有对象

    Object page(UserPageRequest userPageRequest);
}
