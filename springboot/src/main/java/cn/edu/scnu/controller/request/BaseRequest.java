package cn.edu.scnu.controller.request;

import lombok.Data;

/**
 * BaseRequest: 前端传递参数的封装基类
 */
@Data
public class BaseRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
