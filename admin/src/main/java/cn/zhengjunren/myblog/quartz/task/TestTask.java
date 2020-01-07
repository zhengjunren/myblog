package cn.zhengjunren.myblog.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 测试用
 * @author Zheng Jie
 * @date 2019-01-08
 */
@Slf4j
@Component
public class TestTask {

    public void run(){
        log.info("执行时间为：{}", new Date());
    }

    public void run1(String str){
        log.info("执行成功，参数为： {}" + str);
    }
}
