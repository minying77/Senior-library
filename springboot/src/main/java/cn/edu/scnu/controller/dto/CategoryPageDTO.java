package cn.edu.scnu.controller.dto;

import cn.edu.scnu.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 封装Category page方法的返回结果类: 带有二级分类的树形结构
 */

@Data
public class CategoryPageDTO {
    private Integer id;
    private String name;
    private String remark;
    private Integer pid;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;

    private List<Category> children;
}
