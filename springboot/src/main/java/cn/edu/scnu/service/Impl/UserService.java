package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.service.IUserService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;

    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public List<User> page(UserPageRequest userPageRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        Integer pageNum = userPageRequest.getPageNum();
        Integer pageSize = userPageRequest.getPageSize();

        Page<User> userPage = new Page<>(pageNum, pageSize);
        Page<User> pageUserList = userMapper.selectPage(userPage, queryWrapper);
        return pageUserList.getRecords();
    }

    @Override
    public void addUser(User user) {
        Date date = new Date();
//        user.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        user.setUserNo(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
        userMapper.insert(user);
    }

    @Override
    public User getUserById(Integer id) {

        return userMapper.selectById(id);

    }

    @Override
    public void updateUserById(User user) {
        log.info(String.valueOf(user));
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }


}
