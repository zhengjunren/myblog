package cn.zhengjunren.myblog.admin.aspect;

import cn.zhengjunren.myblog.admin.domain.Log;
import cn.zhengjunren.myblog.admin.service.LogService;
import cn.zhengjunren.myblog.admin.utils.SecurityUtil;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.utils.RequestHolder;
import cn.zhengjunren.myblog.common.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>ClassName: LogAspect</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/22 22:37
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(cn.zhengjunren.myblog.common.annotation.MyLog)")
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
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = point.proceed();
        Log log = new Log(Consts.INFO_LOG_TYPE,System.currentTimeMillis() - currentTime.get());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), request, point, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws Exception {
        System.out.println(currentTime.get());
        Log log = new Log(Consts.ERROR_LOG_TYPE,System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logService.save(getUsername(), request, (ProceedingJoinPoint)joinPoint, log);
    }

    public String getUsername() {
        try {
            return SecurityUtil.getCurrentUsername();
        }catch (Exception e){
            return "";
        }
    }

}

