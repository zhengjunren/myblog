package cn.zhengjunren.myblog.quartz.task;

import cn.zhengjunren.myblog.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: LogTask</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/5 20:59
 */
@Slf4j
@Component
public class LogTask {

    private final LogService logService;

    public LogTask(LogService logService) {
        this.logService = logService;
    }

    public void run(){
        logService.deleteAMonthAgo();
        log.info("执行删除任务成功");
    }
}
