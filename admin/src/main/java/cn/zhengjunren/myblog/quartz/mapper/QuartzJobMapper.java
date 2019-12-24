package cn.zhengjunren.myblog.quartz.mapper;

import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface QuartzJobMapper extends BaseMapper<QuartzJob> {

    List<QuartzJob> findByIsPauseIsFalse();
}
