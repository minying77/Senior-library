package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.AdminPass;
import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.entity.Reply;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.AdminMapper;
import cn.edu.scnu.mapper.ReplyMapper;
import cn.edu.scnu.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.edu.scnu.controller.adminController.securePass;

@Slf4j
@Service
public class AdminService implements IAdminService {

    @Resource
    AdminMapper adminMapper;

    @Resource
    ReplyMapper replyMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.selectList(null);
    }

    @Override
    public Page<Admin> page(AdminPageRequest adminPageRequest) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();

        if(adminPageRequest.getName() != null){
            queryWrapper.like("name", adminPageRequest.getName());
        }

        if(adminPageRequest.getEmail() != null){
            queryWrapper.like("email", adminPageRequest.getEmail());
        }

        if(adminPageRequest.getPhone() != null){
            queryWrapper.like("phone", adminPageRequest.getPhone());
        }

        Integer pageNum = adminPageRequest.getPageNum();
        Integer pageSize = adminPageRequest.getPageSize();

        Page<Admin> adminPage = new Page<>(pageNum, pageSize);
        Page<Admin> pageUserList = adminMapper.selectPage(adminPage, queryWrapper);

        return pageUserList;
    }

    @Override
    public Admin getAdminById(Integer id) {
        return adminMapper.selectById(id);
    }

    @Override
    public void addAdmin(Admin admin) {
        //Date date = new Date();
        //admin.setUsername(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));

       try{
           adminMapper.insert(admin);
       } catch (DuplicateKeyException e) {
           // 捕获新增的用户名重复错误
           throw new ServiceException("用户名重复");
       }
    }

    @Override
    public void updateAdminById(Admin admin) {
        //log.info(String.valueOf(user));
        admin.setUpdatetime(new Date());
        adminMapper.updateById(admin);
    }

    @Override
    public void deleteById(Integer id) {
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", id);
        replyMapper.delete(queryWrapper);
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {

        log.info(loginRequest.getPassword()+loginRequest.getName());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginRequest.getName());


        //queryWrapper.eq("password", loginRequest.getPassword());
        Admin admin = adminMapper.selectOne(queryWrapper);

        if(admin == null){
            throw new ServiceException("用户名不存在");
        }
        //System.out.println(admin.getPassword());
        log.info(admin.getPassword());
        log.info(loginRequest.getPassword());
        if(!admin.getPassword().equals(loginRequest.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }

        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);

        return loginDTO;


    }

    @Override
    public void updatePass(AdminPass adminPass) {
        Admin admin = new Admin();
        admin.setId(adminPass.getId());
        admin.setPassword(adminPass.getNewPass());
        adminMapper.updateById(admin);
    }
}
