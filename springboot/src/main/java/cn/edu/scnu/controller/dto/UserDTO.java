package cn.edu.scnu.controller.dto;

import lombok.Data;

/**
 * 用于封装user充值请求数据
 */

@Data
public class UserDTO {
    private Integer id;
    private Integer account;
    private Integer score;
}
