package cn.zhengjunren.myblog.security.configure;

import cn.zhengjunren.myblog.security.domain.TbUser;
import cn.zhengjunren.myblog.security.service.TbUserService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Autowired
    private TbUserService tbUserService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名获取用户
        TbUser tbUser = tbUserService.getByUsername(s);
        //系统中存在该用户，则将用户提交spring-security托管，权限暂时未定
        if (tbUser != null) {
            List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
            grantedAuthorities.add(grantedAuthority);
            return new User(tbUser.getUsername(), tbUser.getPassword(), grantedAuthorities);
        }
        // 用户名不匹配
        else {
            return null;
        }
    }
}
