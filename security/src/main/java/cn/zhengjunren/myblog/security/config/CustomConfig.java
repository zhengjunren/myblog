package cn.zhengjunren.myblog.security.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName: CustomConfig</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 11:18
 */
@Data
@Component
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;

    public CustomConfig() {
        IgnoreConfig ignores = new IgnoreConfig();
        List<String> post = new ArrayList<>();
        post.add("/api/auth/login");
        post.add("/api/auth/logout");
        List<String> pa = new ArrayList<>();
        pa.add("/test/");
        ignores.setPost(post);
        ignores.setPattern(pa);
        this.ignores = ignores;
    }
}
