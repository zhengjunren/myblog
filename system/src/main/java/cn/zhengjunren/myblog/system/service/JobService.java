package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.JobAndTrigger;
import cn.zhengjunren.myblog.system.dto.JobForm;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;

/**
 * <p>ClassName: JobService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:36
 */
public interface JobService {
    /**
     * 添加并启动定时任务
     *
     * @param form 表单参数 {@link JobForm}
     * @throws Exception 异常
     */
    void addJob(JobForm form) throws Exception;

    /**
     * 删除定时任务
     *
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException 异常
     */
    boolean deleteJob(JobForm form) throws SchedulerException;

    /**
     * 暂停定时任务
     *
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException 异常
     */
    void pauseJob(JobForm form) throws SchedulerException;

    /**
     * 恢复定时任务
     *
     * @param form 表单参数 {@link JobForm}
     * @throws SchedulerException 异常
     */
    void resumeJob(JobForm form) throws SchedulerException;

    /**
     * 重新配置定时任务
     *
     * @param form 表单参数 {@link JobForm}
     * @throws Exception 异常
     */
    void cronJob(JobForm form) throws Exception;

    /**
     * 查询定时任务列表
     *
     * @param currentPage 当前页
     * @param pageSize    每页条数
     * @return 定时任务列表
     */
    PageInfo<JobAndTrigger> list(Integer currentPage, Integer pageSize);
}

