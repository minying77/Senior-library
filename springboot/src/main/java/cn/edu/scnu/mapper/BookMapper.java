package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("SELECT * from book WHERE id=#{id}")
    Book getByNo(Integer id);

    @Select("SELECT COUNT(*) FROM book WHERE category_id = #{categoryId}")
    int selectBookCountByCategoryId(Integer categoryId);
}
