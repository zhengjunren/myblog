package cn.zhengjunren.myblog.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>ClassName: SecurityApplication</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:47
 */
@SpringBootApplication(scanBasePackages = "cn.zhengjunren.myblog")
@MapperScan({"cn.zhengjunren.myblog.security.mapper", "cn.zhengjunren.myblog.commons.log.mapper"})
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
