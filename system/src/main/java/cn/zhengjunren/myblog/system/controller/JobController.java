package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.system.domain.JobAndTrigger;
import cn.zhengjunren.myblog.system.dto.JobForm;
import cn.zhengjunren.myblog.system.dto.JobInfo;
import cn.zhengjunren.myblog.system.service.JobService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin(origins = "*", maxAge = 3600L)
public class JobController {

    @Autowired
    private JobService jobService;

    @MyLog("获取任务列表")
    @GetMapping
    public ResponseResult<JobInfo> jobList(Integer page, Integer limit) {

        PageInfo<JobAndTrigger> all = jobService.list(page, limit);
        JobInfo jobInfo = new JobInfo();
        jobInfo.setTotal(all.getTotal());
        jobInfo.setItems(all.getList());
        return new ResponseResult<JobInfo>(ResponseResult.CodeStatus.OK, "任务列表获取成功", jobInfo);
    }

    @MyLog("删除任务")
    @DeleteMapping
    public ResponseResult<Void> delete(@RequestBody JobForm jobForm) throws SchedulerException {
        jobService.deleteJob(jobForm);
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "删除任务失败");
    }

    @MyLog("创建任务")
    @PostMapping
    public ResponseResult<Void> create(@RequestBody JobForm jobForm) {
        try {
            jobService.addJob(jobForm);
        } catch (Exception e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "创建任务失败");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "创建任务成功");
    }

    /**
     * 暂停定时任务
     */
    @MyLog("暂停任务")
    @PutMapping("pause")
    public ResponseResult<Void> pauseJob(@RequestBody JobForm form) {
        try {
            jobService.pauseJob(form);
        } catch (SchedulerException e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "暂停任务失败");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "暂停任务成功");
    }

    /**
     * 恢复定时任务
     */
    @MyLog("恢复任务")
    @PutMapping("resume")
    public ResponseResult<Void> resumeJob(@RequestBody JobForm form) {
        try {
            jobService.resumeJob(form);
        } catch (SchedulerException e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "恢复任务失败");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "恢复任务成功");
    }


    /**
     * 修改定时任务，定时时间
     */
    @MyLog("修改任务")
    @PutMapping("cron")
    public ResponseResult<Void> cronJob(@RequestBody JobForm form) {
        try {
            jobService.cronJob(form);
        } catch (Exception e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改任务失败");
        }

        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "修改任务成功");
    }

}
