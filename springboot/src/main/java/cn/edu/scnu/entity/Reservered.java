package cn.edu.scnu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("reservered")
public class Reservered {
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer seatId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reservedDate;
    private String reservedSlot;
    private String status;
}
