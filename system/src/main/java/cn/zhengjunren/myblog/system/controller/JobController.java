package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.JobAndTrigger;
import cn.zhengjunren.myblog.system.dto.JobInfo;
import cn.zhengjunren.myblog.system.service.JobService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: JobController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:46
 */
@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseResult<JobInfo> jobList(Integer currentPage, Integer pageSize) {

        PageInfo<JobAndTrigger> all = jobService.list(currentPage, pageSize);
        JobInfo jobInfo = new JobInfo();
        jobInfo.setTotal(all.getTotal());
        jobInfo.setItem(all.getList());
        return new ResponseResult<JobInfo>(ResponseResult.CodeStatus.OK, "任务列表获取成功", jobInfo);
    }
}
