package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.JobAndTrigger;

import java.util.List;

/**
 * <p>ClassName: JobMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:35
 */
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
