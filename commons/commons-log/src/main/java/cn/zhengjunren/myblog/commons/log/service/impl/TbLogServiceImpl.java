package cn.zhengjunren.myblog.commons.log.service.impl;

import cn.hutool.json.JSONObject;
import cn.zhengjunren.myblog.commons.dto.IpInfo;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.mapper.TbLogMapper;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: TbLogServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 10:15
 */

@Service
public class TbLogServiceImpl implements TbLogService {

    private static final String LOGIN_PATH = "login";

//    public static final String KEY="myblog:log";

    @Resource
    private TbLogMapper tbLogMapper;

    @Resource
    private RedisTemplate<String, TbLog> redisTemplate;

    @Override
    public int save(TbLog tbLog) {
        tbLog.setCreateTime(new Date());
        tbLogMapper.insert(tbLog);
        return 0;
    }

    @Override
    public long save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, TbLog log) {
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
        log.setParams(params.toString() + " }");
        if(LOGIN_PATH.equals(signature.getName())){
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
                log.setParams(String.format("{ loginParam: LoginParam(username=%s, password=******) }",username));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        IpInfo ipInfo = UserAgentUtils.getIpInfo(ip);
        log.setAddress(String.format("%s|%s|%s|%s", ipInfo.getCountry(),ipInfo.getRegion(), ipInfo.getCity(), ipInfo.getIsp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setBrowser(browser);
        log.setCreateTime(new Date());
//        Long aLong = redisTemplate.opsForList().rightPush(KEY, log);
        return save(log);
    }

    @Override
    public List<TbLog> selectAll(Timestamp start, Timestamp end) {
        Example example = new Example(TbLog.class);
        example.createCriteria().andBetween("createTime", start,end);
        return tbLogMapper.selectByExample(example);
    }

    @Override
    public int insertBatch(List<TbLog> tbLogs) {
        return tbLogMapper.insertList(tbLogs);
    }
}
