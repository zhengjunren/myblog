package cn.zhengjunren.myblog.log.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.common.utils.UserAgentUtil;
import cn.zhengjunren.myblog.log.domain.Log;
import cn.zhengjunren.myblog.log.dto.InfoLogDto;
import cn.zhengjunren.myblog.log.dto.OwnLogDTO;
import cn.zhengjunren.myblog.log.mapper.LogMapper;
import cn.zhengjunren.myblog.log.service.LogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * @author ZhengJunren
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService{

    @Override
    public long save(String username, HttpServletRequest request, ProceedingJoinPoint joinPoint, Log log) throws Exception {
        String ip = UserAgentUtil.getIpAddr(request);
        String browser = UserAgentUtil.getBrowser(request).toString();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyLog aopLog = method.getAnnotation(MyLog.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);
        String loginPath = "login";
        if(loginPath.equals(signature.getName())){
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("usernameOrEmailOrPhone").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setParams(params.toString() + " }");
        log.setAddress(UserAgentUtil.ip2Region(ip));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setBrowser(browser);
        log.setCreateTime(new Date());
        return baseMapper.insert(log);
    }

    @Override
    public ListInfo page(long pageNum, long pageSize, Timestamp start, Timestamp end, String type) {
        Page<Log> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        boolean flag = start != null && end != null;
        logQueryWrapper.between(flag, Log.COL_CREATE_TIME, start, end);
        logQueryWrapper.eq(Log.COL_LOG_TYPE, type);
        logQueryWrapper.orderByDesc(Log.COL_CREATE_TIME);
        IPage<Log> logIPage = baseMapper.selectPage(page, logQueryWrapper);
        return new ListInfo(logIPage.getRecords(), logIPage.getTotal());
    }

    @Override
    public String getErrorDetail(long id) {
        Log log = baseMapper.selectById(id);
        byte[] errorDetail = log.getExceptionDetail();
        return new String(ObjectUtil.isNotNull(errorDetail) ? errorDetail : "".getBytes());
    }

    @Override
    public List<InfoLogDto> getInfoLogList() {
        return baseMapper.getInfoLogList();
    }

    @Override
    public List<OwnLogDTO> selectDetailByUsername(String username, Integer number) {
        return baseMapper.selectOwnLogDetail(username, number);
    }

    @Override
    public void deleteAMonthAgo() {
        baseMapper.deleteAMonthAgo(getAMonthAgoTime(LocalDateTime.now()));
    }

    private Timestamp getAMonthAgoTime(LocalDateTime now) {
        now = now.minus(30, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return new Timestamp(date.getTime());
    }
}
