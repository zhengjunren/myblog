package cn.zhengjunren.myblog.commons.log.aspect;

import cn.zhengjunren.myblog.commons.dto.IpInfo;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import cn.zhengjunren.myblog.commons.utils.MapperUtils;
import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>ClassName: LogAspect</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 11:17
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final String START_TIME = "request-start";

    @Autowired
    private TbLogService tbLogService;

    private long currentTime = 0L;
    /**
     * 切入点
     */
    @Pointcut("@annotation(cn.zhengjunren.myblog.commons.log.annotation.MyLog)")
    public void log() {

    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        currentTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        TbLog tbLog = new TbLog();
        //设置操作者名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        tbLog.setUsername(username);

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        //请求方法描述
        MethodSignature signature = (MethodSignature)point.getSignature();
        tbLog.setDescription(signature.getMethod().getAnnotation(MyLog.class).value());

        Map<String, String[]> parameterMap = request.getParameterMap();
        //设置请求参数
        tbLog.setParams(MapperUtils.mapToJson(parameterMap));

        //请求方法名
        tbLog.setMethod(String.format("%s.%s",point.getSignature().getDeclaringTypeName(),point.getSignature().getName()));

        //设置请求ip
        tbLog.setRequestIp(UserAgentUtils.getIpAddr(request));
        IpInfo ipInfo = UserAgentUtils.getIpInfo(UserAgentUtils.getIpAddr(request));
        tbLog.setAddress(ipInfo.getCountry() + ipInfo.getCity() + ipInfo.getArea());

        //请求浏览器
        tbLog.setBrowser(UserAgentUtils.getBrowser(request).toString());
        Object result = point.proceed();
        tbLog.setLogType("info");
        tbLog.setTime(System.currentTimeMillis() - currentTime);
        tbLogService.save(tbLog);
        return result;
    }


}
