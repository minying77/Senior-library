package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user")
public class User {
    // 设置主键id自增长
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private String name;    //用户名
//    private String username;    //读者卡号
    private String password;
    private String userNo;
    private Integer age;
    private String sex;
    private String phone;
    private String email;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;
//    private boolean status; // 1:可用 0:禁用
    private String status;
}
