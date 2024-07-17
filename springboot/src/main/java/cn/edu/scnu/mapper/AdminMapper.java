package cn.edu.scnu.mapper;

import cn.edu.scnu.controller.dto.AdminPass;
import cn.edu.scnu.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
