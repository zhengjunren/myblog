package cn.zhengjunren.myblog.quartz.controller;

import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import cn.zhengjunren.myblog.quartz.service.QuartzJobService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class QuartzJobController extends BaseController<QuartzJob, QuartzJobService, BaseQueryPageCondition> {


    public QuartzJobController(QuartzJobService service) {
        super(service);
    }

    @ApiOperation("修改定时任务")
    @PutMapping
    public ResponseEntity<Object> update( @RequestBody QuartzJob resources){
        service.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    public ApiResponse updateIsPause(@PathVariable Long id){
        service.updateIsPause(service.getById(id));
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    public ApiResponse execution(@PathVariable Long id){
        service.execution(service.getById(id));
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("删除定时任务")
    @DeleteMapping
    public ApiResponse delete(@RequestBody Set<Long> ids){
        service.delete(ids);
        return ApiResponse.ofSuccess();
    }
}
