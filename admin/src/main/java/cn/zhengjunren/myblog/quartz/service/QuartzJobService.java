package cn.zhengjunren.myblog.quartz.service;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Timestamp;
import java.util.List;
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

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页的条数
     * @param start 开始时间
     * @param end 结束时间
     * @return {@link List < Log >}
     */
    ListInfo page(long pageNum, long pageSize, Timestamp start, Timestamp end);

}
