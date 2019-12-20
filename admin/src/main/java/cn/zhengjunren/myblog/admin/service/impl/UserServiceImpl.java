package cn.zhengjunren.myblog.admin.service.impl;

import cn.zhengjunren.myblog.admin.domain.User;
import cn.zhengjunren.myblog.admin.mapper.UserMapper;
import cn.zhengjunren.myblog.admin.service.UserService;
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
