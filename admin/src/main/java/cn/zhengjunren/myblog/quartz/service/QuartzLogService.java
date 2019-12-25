package cn.zhengjunren.myblog.quartz.service;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.quartz.domain.QuartzLog;
import cn.zhengjunren.myblog.quartz.dto.QuartzLogQueryCondition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ZhengJunren
 */
public interface QuartzLogService extends IService<QuartzLog>{

    ListInfo page(QuartzLogQueryCondition condition);
}
