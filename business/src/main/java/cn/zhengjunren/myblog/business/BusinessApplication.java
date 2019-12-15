package cn.zhengjunren.myblog.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>ClassName: BusinessApplication</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 12:25
 */
@SpringBootApplication(scanBasePackages = "cn.zhengjunren.myblog")
@MapperScan(basePackages = "cn.zhengjunren.myblog.security.mapper")
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
