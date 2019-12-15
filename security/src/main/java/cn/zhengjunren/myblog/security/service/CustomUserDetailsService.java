package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.security.UserPrincipal;
import cn.zhengjunren.myblog.security.domain.Permission;
import cn.zhengjunren.myblog.security.domain.Role;
import cn.zhengjunren.myblog.security.domain.User;
import cn.zhengjunren.myblog.security.mapper.PermissionMapper;
import cn.zhengjunren.myblog.security.mapper.RoleMapper;
import cn.zhengjunren.myblog.security.mapper.UserMapper;
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
 * @date 2019/12/15 13:10
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
        QueryWrapper<User> queryUser = new QueryWrapper<>();
        queryUser
                .eq("username", usernameOrEmailOrPhone)
                .or()
                .eq("email", usernameOrEmailOrPhone)
                .or()
                .eq("phone", usernameOrEmailOrPhone);
        User user = userDao.selectOne(queryUser);
        if (user == null){
            throw new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone);
        }
//        User user = userDao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
//                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        List<Role> roles = roleDao.selectList(queryWrapper);
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }
}

