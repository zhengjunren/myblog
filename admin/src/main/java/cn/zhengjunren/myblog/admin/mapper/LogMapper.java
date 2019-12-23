package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.domain.Log;
import cn.zhengjunren.myblog.admin.dto.log.InfoLogDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface LogMapper extends BaseMapper<Log> {

    List<InfoLogDto>  getInfoLogList();

}
