package cn.edu.scnu.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookDTO {
    private Integer id;
    private String title;
    private String description;
    private String publishDate;
    private String author;
    private String publisher;
    private String isbn;
    private String cover;
    private String price;
    private Integer storeNum;
    private Integer storingNum;

    // 封装在booklist的显示上
    private String category;

    //存储bookadd的分类数据
    private List<String> categories;

    private Integer score;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

}
