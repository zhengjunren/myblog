package cn.zhengjunren.myblog.security.configure;

import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.commons.domain.TbUser;
import cn.zhengjunren.myblog.security.domain.TbRole;
import cn.zhengjunren.myblog.security.service.TbPermissionService;
import cn.zhengjunren.myblog.security.service.TbRoleService;
import cn.zhengjunren.myblog.security.service.TbUserService;
import org.assertj.core.util.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>ClassName: UserDetailsServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:51
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private TbUserService tbUserService;

    @Resource
    private TbRoleService tbRoleService;

    @Resource
    private TbPermissionService tbPermissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名获取用户
        TbUser tbUser = tbUserService.getByUsername(s);
        //系统中存在该用户，则将用户提交spring-security托管，权限暂时未定
        if (tbUser != null) {
            List<TbRole> roles = tbRoleService.getRoleByUsername(s);
            List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
            for (TbRole role : roles) {
                List<TbPermission> permissions = tbPermissionService.selectByRole(role.getEnname());
                for (TbPermission permission : permissions) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getEnname()));
                }
            }
            return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
        }
        // 用户名不匹配
        else {
            return null;
        }
    }
}
