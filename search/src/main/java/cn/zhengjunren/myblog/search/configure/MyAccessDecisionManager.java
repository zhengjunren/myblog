package cn.zhengjunren.myblog.search.configure;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>ClassName: MyAccessDecisionManager</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/23 11:45
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {

        //collection为资源授权器提供的，被访问URL所需权限FilterInvocationSecurityMetadataSource

        //判断URL所需的权限集合是否为空，为空则放行
        if (null == collection || collection.size() <= 0) {
            return;
        }
        String needPermission;
        for (ConfigAttribute c : collection) {
            //获得所需的权限
            needPermission = c.getAttribute();
            //遍历用户拥有的权限进行对比
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needPermission.trim().equals(ga.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("no permission");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
