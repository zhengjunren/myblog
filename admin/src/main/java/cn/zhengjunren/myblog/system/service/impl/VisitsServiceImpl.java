package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.Visits;
import cn.zhengjunren.myblog.system.mapper.VisitsMapper;
import cn.zhengjunren.myblog.system.service.VisitsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 * @author ZhengJunren
 */
@Service
public class VisitsServiceImpl extends ServiceImpl<VisitsMapper, Visits> implements VisitsService{

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        QueryWrapper<Visits> visitsQueryWrapper = new QueryWrapper<>();
        visitsQueryWrapper.eq(Visits.COL_DATE, localDate.toString());
        Visits visits = baseMapper.selectOne(visitsQueryWrapper);
        visits.setPvCounts(visits.getPvCounts()+1);
        long ipCounts = baseMapper.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        saveOrUpdate(visits);
//        baseMapper.updateById(visits);
    }
}
