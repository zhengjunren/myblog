package cn.zhengjunren.myblog.commons.log.service;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import org.aspectj.lang.ProceedingJoinPoint;

import java.sql.Timestamp;
import java.util.List;

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

    /**
     * 保存
     * @param username 用户名
     * @param browser 浏览器
     * @param ip ip地址
     * @param joinPoint {@link ProceedingJoinPoint}
     * @param log {@link TbLog}
     * @return 结果
     */
    int save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, TbLog log);

    /**
     * 获取日志
     * @param start 起始时间
     * @param end 截止时间
     * @return {@link List<TbLog>}
     */
    List<TbLog> selectAll(Timestamp start, Timestamp end);
}
