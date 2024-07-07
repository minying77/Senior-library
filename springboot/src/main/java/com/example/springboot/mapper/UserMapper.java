package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
//提供数据库访问方法给外部调用
public interface UserMapper {
//    @Select("select * from user")
    List<User> list();//查询所有对象

    List<User> listByCondition(UserPageRequest userPageRequest);
}
