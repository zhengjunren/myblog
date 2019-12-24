package cn.zhengjunren.myblog.security.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import cn.zhengjunren.myblog.common.consts.Consts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>ClassName: SecurityUtil</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 22:54
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户用户名
     *
     * @return 当前登录用户用户名
     */
    public static String getCurrentUsername() {
        UserPrincipal currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public static UserPrincipal getCurrentUser() {
        Object userInfo = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (userInfo instanceof UserDetails) {
            return (UserPrincipal) userInfo;
        }
        return null;
    }
}

