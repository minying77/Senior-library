package cn.edu.scnu.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 封装Borrow page方法的返回结果类: 带有二级分类的树形结构
 */

@Data
public class BorrowPageDTO {
    private Integer id;

    private Integer userId; //用户id
    private String userNo;  //会员码
    private String name;    //用户名称
    private String phone;   //用户联系方式

    private Integer bookId; //图书id
    private String title;   //图书名称
    private String isbn;    //图书编码
    private Integer score;  //图书所用积分
    private String price;   // 押金

    private String status;
    private Integer times;  //天数

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date shouldReturnDate;  // 应归还日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnedDate;  // 已归还日期，未归还null

    private String note;
}
