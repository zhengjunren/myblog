package cn.zhengjunren.myblog.commons.log.aspect;

import cn.zhengjunren.myblog.commons.utils.MapperUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        currentTime = System.currentTimeMillis();
        System.out.println("请求url"+ request.getRequestURL());
        log.info("【请求 耗时】：{}", System.currentTimeMillis() - currentTime);
        log.info("【请求 URL】：{}", request.getRequestURL());
        log.info("【请求 IP】：{}", request.getRemoteAddr());
        log.info("【请求类名】：{}，【请求方法名】：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());

        Map<String, String[]> parameterMap = request.getParameterMap();

        log.info("【请求参数】：{}，", MapperUtils.mapToJson(parameterMap));
//        log.info("【请求参数】：{}，", JSONUtil.toJsonStr(parameterMap));

        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
        Object result = point.proceed();
        log.info("【返回值】：{}", MapperUtils.obj2json(result));
        return result;
    }
}
