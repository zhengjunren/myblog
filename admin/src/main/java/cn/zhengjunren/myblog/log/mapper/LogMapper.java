package cn.zhengjunren.myblog.log.mapper;

import cn.zhengjunren.myblog.log.domain.Log;
import cn.zhengjunren.myblog.log.dto.InfoLogDto;
import cn.zhengjunren.myblog.log.dto.OwnLogDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface LogMapper extends BaseMapper<Log> {

    /**
     * 获取info类型的数据
     * @return /
     */
    List<InfoLogDto>  getInfoLogList();

    /**
     * 查询用户的行为
     * @param username 用户名
     * @param number 条数
     * @return {@link OwnLogDTO}
     */
    List<OwnLogDTO> selectOwnLogDetail(@Param("username") String username, @Param("number") Integer number);
}
