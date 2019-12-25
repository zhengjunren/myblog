package cn.zhengjunren.myblog.quartz.service.impl;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.quartz.domain.QuartzLog;
import cn.zhengjunren.myblog.quartz.dto.QuartzLogQueryCondition;
import cn.zhengjunren.myblog.quartz.mapper.QuartzLogMapper;
import cn.zhengjunren.myblog.quartz.service.QuartzLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class QuartzLogServiceImpl extends ServiceImpl<QuartzLogMapper, QuartzLog> implements QuartzLogService{

    @Override
    public ListInfo page(QuartzLogQueryCondition condition) {
        Page<QuartzLog> page = new Page<>(condition.getPage(), condition.getLimit());
        QueryWrapper<QuartzLog> quartzLogQueryWrapper = new QueryWrapper<>();
        boolean flag = condition.getStart() != null && condition.getEnd() != null;
        quartzLogQueryWrapper.between(flag, QuartzLog.COL_CREATE_TIME, condition.getStart(), condition.getEnd());
        if (condition.getIsSuccess() != null){
            quartzLogQueryWrapper.eq(QuartzLog.COL_IS_SUCCESS, condition.getIsSuccess());
        }
        quartzLogQueryWrapper.orderByDesc(QuartzLog.COL_CREATE_TIME);
        IPage<QuartzLog> quartzLogIPage = baseMapper.selectPage(page, quartzLogQueryWrapper);
        return new ListInfo(quartzLogIPage.getRecords(), quartzLogIPage.getTotal());
    }
}
