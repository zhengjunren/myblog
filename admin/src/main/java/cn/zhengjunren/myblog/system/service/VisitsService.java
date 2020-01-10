package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.Visits;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZhengJunren
 */
public interface VisitsService extends IService<Visits>{

    /**
     * 提供给定时任务，每天0点执行
     */
    void save();

    /**
     * 新增记录
     * @param request /
     */
    @Async
    void count(HttpServletRequest request);

    /**
     * 获取数据
     * @return /
     */
    Object get();

    /**
     * getChartData
     * @return /
     */
    Object getChartData();
}
