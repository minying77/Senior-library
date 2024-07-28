package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
@TableName("borrow")
public class Borrow {
    // 设置主键id自增长
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer bookId;
    private Integer userId;
    private String status;
    private Integer times;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnedtime;

}
