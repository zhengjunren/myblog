package cn.zhengjunren.myblog.system.job;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import cn.zhengjunren.myblog.system.job.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>ClassName: TestJob</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:34
 */
@Slf4j
@Component
public class RegularGetLogFromRedisJob implements BaseJob {

    public RegularGetLogFromRedisJob() {
    }

    @Autowired
    private TbLogService tbLogService;

    @Resource
    private RedisTemplate<String, TbLog> redisTemplate;


    @Override
    public void execute(JobExecutionContext context) {
        Long size = redisTemplate.opsForList().size(TbLogService.KEY);
//        if (size != null && size < 500L && size > 0){
//            List<TbLog> list = redisTemplate.opsForList().range(TbLogService.KEY,0,size);
//            if (list != null) {
//                redisTemplate.opsForList().remove(TbLogService.KEY, size, list.get(list.size()-1));
//                tbLogService.insertBatch(list);
//            }
//        }
//        else if (size != null &&size > 500L){
//            long count = size % 500L;
//            List<TbLog> list = redisTemplate.opsForList().range(TbLogService.KEY,0,500);
//            for (long i = 0; i < count; i++) {
//                if (list != null) {
//                    redisTemplate.opsForList().remove(TbLogService.KEY, size, list.get(list.size()-1));
//                    tbLogService.insertBatch(list);
//                }
//            }
//        }
//        else {
//            log.error("获取不到缓存的大小");
//        }
    }
}
