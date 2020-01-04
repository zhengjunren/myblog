package cn.zhengjunren.myblog.quartz.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import cn.zhengjunren.myblog.quartz.dto.QuartzQueryCondition;
import cn.zhengjunren.myblog.quartz.service.QuartzJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <p>ClassName: QuartzJobController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/24 21:55
 */
@RestController
@RequestMapping("/api/jobs")
@Api(tags = "任务管理")
public class QuartzJobController extends BaseController<QuartzJob, QuartzJobService, QuartzQueryCondition> {


    public QuartzJobController(QuartzJobService service) {
        super(service);
    }

    @MyLog("查询任务")
    @Override
    @GetMapping
    public ApiResponse page(QuartzQueryCondition condition) {
        return ApiResponse.ofSuccess(service.page(condition.getPage(), condition.getLimit(), condition.getStart(), condition.getEnd()));
    }

    @MyLog("修改定时任务")
    @PutMapping
    @ApiOperation("修改定时任务")
    public ApiResponse update( @RequestBody QuartzJob resources){
        service.update(resources);
        return ApiResponse.ofSuccess();
    }

    @MyLog("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @ApiOperation("更改定时任务状态")
    public ApiResponse updateIsPause(@PathVariable Long id){
        service.updateIsPause(service.getById(id));
        return ApiResponse.ofSuccess();
    }

    @MyLog("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @ApiOperation("执行定时任务")
    public ApiResponse execute(@PathVariable Long id){
        service.execution(service.getById(id));
        return ApiResponse.ofSuccess();
    }

    @MyLog("删除定时任务")
    @DeleteMapping
    @ApiOperation("删除定时任务")
    public ApiResponse delete(@RequestBody Set<Long> ids){
        service.delete(ids);
        return ApiResponse.ofSuccess();
    }
}
