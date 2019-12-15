package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.security.domain.User;
import cn.zhengjunren.myblog.security.mapper.UserMapper;
import cn.zhengjunren.myblog.security.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ZhengJunren
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
