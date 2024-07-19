package cn.edu.scnu.controller.request;

import lombok.Data;

@Data
public class BookPageRequest extends BaseRequest{
    private String title;
    private String ISBN;
}
