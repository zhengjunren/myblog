package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Permission;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.domain.User;
import cn.zhengjunren.myblog.admin.mapper.PermissionMapper;
import cn.zhengjunren.myblog.admin.mapper.RoleMapper;
import cn.zhengjunren.myblog.admin.mapper.UserMapper;
import cn.zhengjunren.myblog.admin.vo.UserPrincipal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserMapper userDao;

    @Autowired
    private RoleMapper roleDao;

    @Autowired
    private PermissionMapper permissionDao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username", usernameOrEmailOrPhone)
                .or().eq("email", usernameOrEmailOrPhone)
                .or().eq("phone", usernameOrEmailOrPhone);
        User user = userDao.selectOne(userQueryWrapper);
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
