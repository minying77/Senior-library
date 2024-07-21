package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.Message;
import cn.edu.scnu.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
}
