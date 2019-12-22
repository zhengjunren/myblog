package cn.zhengjunren.myblog.admin.service.impl;

import cn.zhengjunren.myblog.admin.domain.Log;
import cn.zhengjunren.myblog.admin.mapper.LogMapper;
import cn.zhengjunren.myblog.admin.service.LogService;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.utils.UserAgentUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author ZhengJunren
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService{

    private static final String LOGIN_PATH = "login";

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
        log.setParams(params.toString() + " }");
//        if(LOGIN_PATH.equals(signature.getName())){
//            try {
//                assert argValues != null;
//                username = new JSONObject(argValues[0]).get("username").toString();
//                log.setParams(String.format("{ loginParam: LoginParam(username=%s, password=******) }",username));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
        log.setAddress(UserAgentUtil.ip2Region(ip));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setBrowser(browser);
        log.setCreateTime(new Date());
        return baseMapper.insert(log);
    }
}
