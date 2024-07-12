package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("book")
public class Book {
    // 设置主键id自增长
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
//    private String name;
    private String title;
    private String description;
//    private String publishDate;
    private String publishDate;
    private String author;
    private String publisher;
//    private String bookNo;
    private String isbn;
    private String cover;
    private String price;
    private Integer storeNum;
    private Integer storingNum;
    private Integer categoryId;
    private Integer score;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;


}
