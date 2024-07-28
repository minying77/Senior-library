package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * from user WHERE user_No=#{userNo}")
    User getByID(Integer userNo);

    @Select("SELECT * from user WHERE name=#{name}")
    User selectByName(String name);

}
