package cn.zhengjunren.myblog.system.configure;

import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * <p>ClassName: MyFilterInvocationSecurityMetadataSource</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/23 11:42
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    TbPermissionService tbPermissionService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private volatile HashMap<String, Collection<ConfigAttribute>> map = null;

    public void loadResourceDefine() {
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<TbPermission> permissions = tbPermissionService.selectAll();
        for (TbPermission permission : permissions) {
            array = new ArrayList<>();
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，
            //例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            cfg = new SecurityConfig(permission.getEnname());
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value
            map.put(permission.getUrl(), array);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(map ==null) {
            loadResourceDefine();
        }
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        String uri = request.getRequestURI();
        for (String url : map.keySet()) {
            if (antPathMatcher.match(url, uri)){
                return map.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
