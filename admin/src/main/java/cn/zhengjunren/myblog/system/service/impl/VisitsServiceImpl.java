package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.system.domain.Visits;
import cn.zhengjunren.myblog.system.mapper.VisitsMapper;
import cn.zhengjunren.myblog.system.service.VisitsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhengJunren
 */
@Service
public class VisitsServiceImpl extends ServiceImpl<VisitsMapper, Visits> implements VisitsService{

    @Override
    public void save() {
        LocalDate localDate = LocalDate.now();
        Visits visits = baseMapper.findByDate(localDate.toString());
        if(visits == null){
            visits = new Visits();
            visits.setWeekDay(Consts.WEEKDAY[DateUtil.thisDayOfWeek() - 1]);
            visits.setPvCounts(1L);
            visits.setIpCounts(1L);
            visits.setDate(localDate.toString());
            visits.setCreateTime(new Date());
            baseMapper.insert(visits);
        }
    }

    @Override
    public void count(HttpServletRequest request) {
        LocalDate localDate = LocalDate.now();
        Visits visits = baseMapper.findByDate(localDate.toString());
        visits.setPvCounts(visits.getPvCounts()+1);
        long ipCounts = baseMapper.findIp(localDate.toString(), localDate.plusDays(1).toString());
        visits.setIpCounts(ipCounts);
        saveOrUpdate(visits);
    }

    @Override
    public Object get() {
        Map<String,Object> map = new HashMap<>(4);
        LocalDate localDate = LocalDate.now();
        Visits visits = baseMapper.findByDate(localDate.toString());
        QueryWrapper<Visits> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(Visits.COL_CREATE_TIME, localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        List<Visits> list = baseMapper.selectList(queryWrapper);
//        visitsRepository.findAllVisits(localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        long recentVisits = 0, recentIp = 0;
        for (Visits data : list) {
            recentVisits += data.getPvCounts();
            recentIp += data.getIpCounts();
        }
        map.put("newVisits",visits.getPvCounts());
        map.put("newIp",visits.getIpCounts());
        map.put("recentVisits",recentVisits);
        map.put("recentIp",recentIp);
        return map;
    }

    @Override
    public Object getChartData() {
        Map<String,Object> map = new HashMap<>(3);
        LocalDate localDate = LocalDate.now();
        QueryWrapper<Visits> queryWrapper = new QueryWrapper<>();
        queryWrapper.between(Visits.COL_CREATE_TIME, localDate.minusDays(6).toString(),localDate.plusDays(1).toString());
        List<Visits> list = baseMapper.selectList(queryWrapper);
        map.put("weekDays",list.stream().map(Visits::getWeekDay).collect(Collectors.toList()));
        map.put("visitsData",list.stream().map(Visits::getPvCounts).collect(Collectors.toList()));
        map.put("ipData",list.stream().map(Visits::getIpCounts).collect(Collectors.toList()));
        return map;
    }
}
