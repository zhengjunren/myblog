package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.User;
import cn.zhengjunren.myblog.system.mapper.UserMapper;
import cn.zhengjunren.myblog.system.service.UserService;
import cn.zhengjunren.myblog.common.consts.Consts;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = Consts.USER_CACHE_NAME)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    @Cacheable(key = "#p0")
    public User selectOneByUsernameOrEmailOrPhone(String usernameOrEmailOrPhone) {
        return baseMapper.selectOneByUsernameOrEmailOrPhone(usernameOrEmailOrPhone);
    }
}
