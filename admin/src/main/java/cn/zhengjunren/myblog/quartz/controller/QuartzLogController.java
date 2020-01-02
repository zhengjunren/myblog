package cn.zhengjunren.myblog.quartz.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.quartz.domain.QuartzLog;
import cn.zhengjunren.myblog.quartz.dto.QuartzLogQueryCondition;
import cn.zhengjunren.myblog.quartz.service.QuartzLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "任务日志管理")
public class QuartzLogController extends BaseController<QuartzLog, QuartzLogService, QuartzLogQueryCondition> {

    public QuartzLogController(QuartzLogService service) {
        super(service);
    }

    @Override
    @GetMapping
    @MyLog("分页查询任务日志")
    @ApiOperation("分页查询")
    public ApiResponse page(QuartzLogQueryCondition condition) {
        return ApiResponse.ofSuccess(service.page(condition));
    }

    @DeleteMapping
    @MyLog("删除任务日志")
    @ApiOperation("删除日志")
    public ApiResponse delete(){
        service.remove(null);
        return ApiResponse.ofSuccess();
    }
}
