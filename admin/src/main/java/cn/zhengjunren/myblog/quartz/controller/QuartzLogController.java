package cn.zhengjunren.myblog.quartz.controller;

import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.quartz.domain.QuartzLog;
import cn.zhengjunren.myblog.quartz.dto.QuartzLogQueryCondition;
import cn.zhengjunren.myblog.quartz.service.QuartzLogService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: QuartzLogController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/25 15:34
 */
@RestController
@RequestMapping("/api/jobs/logs")
public class QuartzLogController extends BaseController<QuartzLog, QuartzLogService, QuartzLogQueryCondition> {

    public QuartzLogController(QuartzLogService service) {
        super(service);
    }

    @Override
    @GetMapping
    public ApiResponse page(QuartzLogQueryCondition condition) {
        return ApiResponse.ofSuccess(service.page(condition));
    }

    @DeleteMapping
    public ApiResponse delete(){
        service.remove(null);
        return ApiResponse.ofSuccess();
    }
}
