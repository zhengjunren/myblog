package cn.zhengjunren.myblog.quartz.service;

import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface QuartzJobService extends IService<QuartzJob>{

    /**
     * 编辑
     * @param resources /
     */
    void update(QuartzJob resources);

    /**
     * 更改定时任务状态
     * @param quartzJob /
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     * @param quartzJob /
     */
    void execution(QuartzJob quartzJob);

    /**
     * 删除任务
     * @param ids id
     */
    void delete(Set<Long> ids);

}
