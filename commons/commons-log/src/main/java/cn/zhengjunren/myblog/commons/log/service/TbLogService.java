package cn.zhengjunren.myblog.commons.log.service;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <p>ClassName: TbLogService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 10:15
 */
public interface TbLogService{

    /**
     * 保存日志
     * @param tbLog {@link TbLog}
     * @return 返回结果
     */
    int save(TbLog tbLog);

    int save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, TbLog log);
}
