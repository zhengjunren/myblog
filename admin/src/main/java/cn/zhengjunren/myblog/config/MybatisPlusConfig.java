package cn.zhengjunren.myblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>ClassName: MybatisPlusConfig</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 21:07
 */
@Configuration
@MapperScan(basePackages = {
    "cn.zhengjunren.myblog.system.mapper",
    "cn.zhengjunren.myblog.log.mapper",
    "cn.zhengjunren.myblog.quartz.mapper"
})
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}

