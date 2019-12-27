package cn.zhengjunren.myblog.quartz.service.impl;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.quartz.domain.QuartzJob;
import cn.zhengjunren.myblog.quartz.mapper.QuartzJobMapper;
import cn.zhengjunren.myblog.quartz.service.QuartzJobService;
import cn.zhengjunren.myblog.quartz.service.QuartzLogService;
import cn.zhengjunren.myblog.quartz.utils.QuartzManage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.CronExpression;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Service(value = "quartzJobService")
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService{


    private final QuartzLogService quartzLogService;

    private final QuartzManage quartzManage;

    public QuartzJobServiceImpl(QuartzLogService quartzLogService, QuartzManage quartzManage) {
        this.quartzLogService = quartzLogService;
        this.quartzManage = quartzManage;
    }

    @Override
    public void update(QuartzJob resources) {
        if(resources.getId().equals(1L)){
            throw new BadRequestException(400, "该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException(400, "cron表达式格式错误");
        }
        baseMapper.updateById(resources);
        quartzManage.updateJobCron(resources);
    }

    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException(400, "该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        }else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        baseMapper.updateById(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException(400, "该任务不可操作");
        }
        quartzManage.runJobNow(quartzJob);
    }

    @Override
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            if(id.equals(1L)){
                throw new BadRequestException(400, "更新访客记录不可删除，你可以在后台代码中取消该限制");
            }
            QuartzJob quartzJob = baseMapper.selectById(id);
            quartzManage.deleteJob(quartzJob);
            baseMapper.deleteById(id);
        }
    }

    @Override
    public ListInfo page(long pageNum, long pageSize, Timestamp start, Timestamp end) {
        Page<QuartzJob> page = new Page<>(pageNum, pageSize);
        QueryWrapper<QuartzJob> quartzJobQueryWrapper = new QueryWrapper<>();
        boolean flag = start != null && end != null;
        quartzJobQueryWrapper.between(flag, QuartzJob.COL_CREATE_TIME, start, end);
        IPage<QuartzJob> quartzJobIPage = baseMapper.selectPage(page, quartzJobQueryWrapper);
        return new ListInfo(quartzJobIPage.getRecords(), quartzJobIPage.getTotal());
    }
}