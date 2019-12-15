package cn.zhengjunren.myblog.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>ClassName: CustomConfig</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 11:18
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
