package cn.zhengjunren.myblog.quartz.task;

import cn.zhengjunren.myblog.system.service.VisitsService;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: VisitsTask</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/10 21:23
 */
@Component
public class VisitsTask {

    private final VisitsService visitsService;

    public VisitsTask(VisitsService visitsService) {
        this.visitsService = visitsService;
    }

    public void run(){
        visitsService.save();
    }
}
