package cn.zhengjunren.myblog.commons.log.aspect;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import cn.zhengjunren.myblog.commons.utils.RequestHolder;
import cn.zhengjunren.myblog.commons.utils.SecurityUtils;
import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
         Object result;
         currentTime = System.currentTimeMillis();
         result = point.proceed();
         TbLog log = new TbLog("INFO",System.currentTimeMillis() - currentTime);
         HttpServletRequest request = RequestHolder.getHttpServletRequest();

         tbLogService.save(getUsername(), UserAgentUtils.getBrowser(request).toString(), UserAgentUtils.getIpAddr(request), point, log);
         return result;
     }

    public String getUsername() {
        try {
            return SecurityUtils.getUsername();
        }catch (Exception e){
            return "";
        }
    }

}
