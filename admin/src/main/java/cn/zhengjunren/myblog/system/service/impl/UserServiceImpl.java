package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.system.domain.User;
import cn.zhengjunren.myblog.system.dto.params.PasswordParams;
import cn.zhengjunren.myblog.system.mapper.UserMapper;
import cn.zhengjunren.myblog.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = Consts.USER_CACHE_NAME)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable(key = "#p0")
    public User selectOneByUsernameOrEmailOrPhone(String usernameOrEmailOrPhone) {
        return baseMapper.selectOneByUsernameOrEmailOrPhone(usernameOrEmailOrPhone);
    }

    @Override
    @CacheEvict(key = "#p0")
    public void updateAvatarByUsername(String username, String avatar) {
        baseMapper.updateAvatarByUsername(username, avatar);
    }

    @Override
    @CacheEvict(key = "#p0")
    public void updatePassword(String username, PasswordParams passwordParams) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(User.COL_USERNAME, username);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (passwordEncoder.matches(passwordParams.getOldPassword(), user.getPassword())) {
            if (passwordParams.getConfirmPassword().equals(passwordParams.getNewPassword())){
                baseMapper.updatePassword(username, passwordEncoder.encode(passwordParams.getNewPassword()));
            }
            else {
                throw new BadRequestException(400, "两次密码不同，请重试！");
            }
        }
        else {
            throw new BadRequestException(400, "原始密码错误，请重试！");
        }
    }

    @Override
    @CacheEvict("#p0")
    public void update(String username, User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(User.COL_USERNAME, username);
        User user1 = baseMapper.selectOne(userQueryWrapper);
        user1.setBirthday(user.getBirthday());
        user1.setNickname(user.getNickname());
        user1.setPhone(user.getPhone());
        user1.setEmail(user.getEmail());
        user1.setSex(user.getSex());
        baseMapper.updateById(user1);
    }
}
