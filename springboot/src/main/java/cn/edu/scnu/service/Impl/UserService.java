package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.request.UserPageRequest;
import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.entity.Message;
import cn.edu.scnu.entity.Reservered;
import cn.edu.scnu.entity.User;
import cn.edu.scnu.mapper.BorrowMapper;
import cn.edu.scnu.mapper.MessageMapper;
import cn.edu.scnu.mapper.ReserveredMapper;
import cn.edu.scnu.mapper.UserMapper;
import cn.edu.scnu.service.IUserService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    @Resource
    UserMapper userMapper;

    @Resource
    ReserveredMapper reserveredMapper;

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    MessageMapper messageMapper;

    public List<User> list() {
        return userMapper.selectList(null);
    }

    @Override
    public Page<User> page(UserPageRequest userPageRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if(userPageRequest.getName() != null){
            queryWrapper.like("name", userPageRequest.getName());
        }

        if(userPageRequest.getPhone() != null){
            queryWrapper.like("phone", userPageRequest.getPhone());
        }


        Integer pageNum = userPageRequest.getPageNum();
        Integer pageSize = userPageRequest.getPageSize();

        Page<User> userPage = new Page<>(pageNum, pageSize);
        Page<User> pageUserList = userMapper.selectPage(userPage, queryWrapper);
        return pageUserList;
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
        log.info(user.getStatus()+" and " + user.getId());
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    @Override
    public void deleteById(Integer id) {
        // 删除该用户的预约信息
        QueryWrapper<Reservered> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", id);
        reserveredMapper.delete(queryWrapper1);

        // 删除该用户的借阅信息
        QueryWrapper<Borrow> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", id);
        borrowMapper.delete(queryWrapper2);

        // 删除该用户的留言信息
        QueryWrapper<Message> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("user_id", id);
        messageMapper.delete(queryWrapper3);

        userMapper.deleteById(id);
    }


}
