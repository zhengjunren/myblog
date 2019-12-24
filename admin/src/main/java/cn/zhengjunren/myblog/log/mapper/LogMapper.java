package cn.zhengjunren.myblog.log.mapper;

import cn.zhengjunren.myblog.log.domain.Log;
import cn.zhengjunren.myblog.log.dto.InfoLogDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface LogMapper extends BaseMapper<Log> {

    List<InfoLogDto>  getInfoLogList();

}
