package cn.zhengjunren.myblog.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>ClassName: SystemApplication</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 23:29
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = "cn.zhengjunren.myblog")
@MapperScan(basePackages = {"cn.zhengjunren.myblog.system.mapper", "cn.zhengjunren.myblog.commons.log.mapper"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
