package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.Visits;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhengJunren
 */
public interface VisitsMapper extends BaseMapper<Visits> {

    /**
     * 获取一个时间段的IP记录
     * @param date1 startTime
     * @param date2 entTime
     * @return IP数目
     */
    Long findIp(@Param("date1") String date1, @Param("date2") String date2);

    /**
     * 根据时间查找
     * @param date 日期
     * @return {@link Visits}
     */
    Visits findByDate(@Param("date") String date);
}
