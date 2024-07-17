package cn.edu.scnu.controller.request;

import lombok.Data;

/**
 * 封装前端admin登录所请求参数
 */

@Data
public class LoginRequest {

    private String name;
    private String password;

}
