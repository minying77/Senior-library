package cn.edu.scnu.controller.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.Serializable;

/**
 * 封装login方法的返回参数
 */
@Data
@CrossOrigin
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String phone;
    private String email;
}
