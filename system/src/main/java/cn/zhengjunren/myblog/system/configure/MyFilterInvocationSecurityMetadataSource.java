package cn.zhengjunren.myblog.system.configure;

import cn.zhengjunren.myblog.system.domain.RoleAndUrl;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
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

    @Autowired
    TbPermissionService tbPermissionService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) o;
        String uri = fi.getHttpRequest().getRequestURI();
        List<RoleAndUrl> urlWithRole = tbPermissionService.getUrlWithRole();
        for (RoleAndUrl roleAndUrl : urlWithRole) {
            if (antPathMatcher.match(roleAndUrl.getUrl(), uri)){
                return SecurityConfig.createList(roleAndUrl.getEnname());
            }
        }
        return SecurityConfig.createList("ROLE_USER");
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
