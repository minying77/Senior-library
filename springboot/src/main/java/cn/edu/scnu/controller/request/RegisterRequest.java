package cn.edu.scnu.controller.request;

import lombok.Data;

/**
 * 封装前端注册所请求参数
 */

@Data
public class RegisterRequest {

    private String name;
    private String password;

}