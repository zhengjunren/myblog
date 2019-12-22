package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhengJunren
 */
public interface LogService extends IService<Log>{

    /**
     * 保存
     * @param username 用户名
     * @param request request
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param log {@link Log}
     * @return 结果
     */
    long save(String username, HttpServletRequest request, ProceedingJoinPoint joinPoint, Log log) throws Exception;
}
