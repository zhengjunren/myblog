package cn.zhengjunren.myblog.system.job;

import cn.zhengjunren.myblog.system.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.quartz.JobExecutionContext;

/**
 * <p>ClassName: TestJob</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:34
 */
@Slf4j
public class TestJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) {
        log.error("Test Job 执行时间: {}", DateUtil.now());
    }
}
