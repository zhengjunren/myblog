package cn.zhengjunren.myblog.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>ClassName: JwtConfig</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 22:02
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {
    /**
     * jwt 加密 key，默认值：xkcoding.
     */
    private String key = "xkcoding";

    /**
     * jwt 过期时间，默认值（单位毫秒）：10800000L {@code 3 小时}.
     */
    private Long ttl = 10800000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;
}
