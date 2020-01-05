package cn.zhengjunren.myblog.log.service;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.log.domain.Log;
import cn.zhengjunren.myblog.log.dto.InfoLogDto;
import cn.zhengjunren.myblog.log.dto.OwnLogDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

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

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页的条数
     * @param start 开始时间
     * @param end 结束时间
     * @return {@link List<Log>}
     */
    ListInfo page(long pageNum, long pageSize, Timestamp start, Timestamp end, String type);

    /**
     * 获取异常信息
     * @param id id
     * @return 结果
     */

    String getErrorDetail(long id);

    /**
     * 获取 info 类型日志
     * @return 结果
     */
    List<InfoLogDto>  getInfoLogList();

    /**
     * 根据用户名获取日志
     * @param username 用户名
     * @param number 条数
     * @return 行为日志
     */
    List<OwnLogDTO> selectDetailByUsername(String username, Integer number);

    /**
     * 删除一个月之前的日志
     */
    void deleteAMonthAgo();
}
