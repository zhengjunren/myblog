package cn.zhengjunren.myblog.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>ClassName: BusinessApplication</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 9:35
 */
@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "cn.zhengjunren.myblog")
@MapperScan({"cn.zhengjunren.myblog.business.mapper","cn.zhengjunren.myblog.commons.log.mapper"})
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
