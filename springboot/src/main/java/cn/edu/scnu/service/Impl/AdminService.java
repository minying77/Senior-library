package cn.edu.scnu.service.Impl;

import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.entity.Admin;
import cn.edu.scnu.exception.ServiceException;
import cn.edu.scnu.mapper.AdminMapper;
import cn.edu.scnu.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static cn.edu.scnu.controller.adminController.securePass;

@Service
public class AdminService implements IAdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.selectList(null);
    }

    @Override
    public List<Admin> page(AdminPageRequest adminPageRequest) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();

        Integer pageNum = adminPageRequest.getPageNum();
        Integer pageSize = adminPageRequest.getPageSize();

        Page<Admin> adminPage = new Page<>(pageNum, pageSize);
        Page<Admin> pageUserList = adminMapper.selectPage(adminPage, queryWrapper);
        return pageUserList.getRecords();
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
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", loginRequest.getUsername());

        //queryWrapper.eq("password", loginRequest.getPassword());

        Admin admin = adminMapper.selectOne(queryWrapper);

        if(admin == null){
            throw new ServiceException("用户名不存在");
        }
        String adminPass = securePass(admin.getPassword());
        if(!loginRequest.getPassword().equals(adminPass)){
            throw new ServiceException("用户名或密码错误");
        }

        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);

        return loginDTO;


    }
}
