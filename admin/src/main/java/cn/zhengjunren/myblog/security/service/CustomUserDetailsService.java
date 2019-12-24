package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.system.domain.Permission;
import cn.zhengjunren.myblog.system.domain.Role;
import cn.zhengjunren.myblog.system.domain.User;
import cn.zhengjunren.myblog.system.service.PermissionService;
import cn.zhengjunren.myblog.system.service.RoleService;
import cn.zhengjunren.myblog.system.service.UserService;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>ClassName: CustomUserDetailsService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 21:40
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userDao;

    private final RoleService roleDao;

    private final PermissionService permissionDao;

    public CustomUserDetailsService(UserService userDao, RoleService roleDao, PermissionService permissionDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.permissionDao = permissionDao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = userDao.selectOneByUsernameOrEmailOrPhone(usernameOrEmailOrPhone);
        if (user == null) {
            throw new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone);
        }
        List<Role> roles = roleDao.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }
}
