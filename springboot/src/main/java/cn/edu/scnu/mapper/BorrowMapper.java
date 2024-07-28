package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.Borrow;
import cn.edu.scnu.mapper.po.BorrowReturCountPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BorrowMapper extends BaseMapper<Borrow> {
    @Select("<script>"
            + "SELECT COUNT(id) as count, DATE_FORMAT(createtime, '%Y-%m-%d') as date "
            + "FROM borrow "
            + "WHERE status = #{status} AND createtime >= "
            + "CASE "
            + "  WHEN #{timeRange} = 'week' THEN DATE_SUB(NOW(), INTERVAL 1 WEEK) "
            + "  WHEN #{timeRange} = 'month' THEN DATE_SUB(NOW(), INTERVAL 1 MONTH) "
            + "  WHEN #{timeRange} = 'month2' THEN DATE_SUB(NOW(), INTERVAL 2 MONTH) "
            + "  WHEN #{timeRange} = 'month3' THEN DATE_SUB(NOW(), INTERVAL 3 MONTH) "
            + "  ELSE NOW() "
            + "END "
            + "GROUP BY date"
            + "</script>")
    @Options(useCache=false)
    List<BorrowReturCountPO> getCountByTimeRange(
            @Param("timeRange") String timeRange,
            @Param("status") int status);
}
