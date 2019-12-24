package cn.zhengjunren.myblog.quartz.config;

import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import cn.zhengjunren.myblog.quartz.mapper.QuartzJobMapper;
import cn.zhengjunren.myblog.quartz.utils.QuartzManage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Component
public class JobRunner implements ApplicationRunner {

    private final QuartzJobMapper quartzJobMapper;

    private final QuartzManage quartzManage;

    public JobRunner(QuartzJobMapper quartzJobMapper, QuartzManage quartzManage) {
        this.quartzJobMapper = quartzJobMapper;
        this.quartzManage = quartzManage;
    }

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        System.out.println("--------------------注入定时任务---------------------");
        List<QuartzJob> quartzJobs = quartzJobMapper.findByIsPauseIsFalse();
        quartzJobs.forEach(quartzManage::addJob);
        System.out.println("--------------------定时任务注入完成---------------------");
    }
}
