package cn.edu.scnu.controller.request;

import lombok.Data;

@Data
public class BorrowPageRequest extends BaseRequest{
    private String title;   // 图书名称
    private String name;    // 用户名称
}
