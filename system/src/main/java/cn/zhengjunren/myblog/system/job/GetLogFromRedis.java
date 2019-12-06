package cn.zhengjunren.myblog.system.job;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import cn.zhengjunren.myblog.system.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>ClassName: GetLogFromRedis</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/6 19:53
 */
@Component
@Slf4j
public class GetLogFromRedis implements BaseJob {

    private final TbLogService tbLogService;

    @Resource
    private RedisTemplate<String, TbLog> redisTemplate;

    public GetLogFromRedis(TbLogService tbLogService) {
        this.tbLogService = tbLogService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("日志条数" + redisTemplate.opsForList().size(TbLogService.KEY));
//        List<TbLog> list = redisTemplate.opsForList().range(TbLogService.KEY,0,2 );
    }
}
