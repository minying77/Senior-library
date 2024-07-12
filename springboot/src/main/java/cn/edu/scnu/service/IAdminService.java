package cn.edu.scnu.service;

import cn.edu.scnu.controller.dto.LoginDTO;
import cn.edu.scnu.controller.request.AdminPageRequest;
import cn.edu.scnu.controller.request.LoginRequest;
import cn.edu.scnu.entity.Admin;

import java.util.List;

public interface IAdminService {
    List<Admin> list();

    List<Admin> page(AdminPageRequest adminPageRequest);

    Admin getAdminById(Integer id);

    void addAdmin(Admin admin);

    void updateAdminById(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest loginRequest);
}
