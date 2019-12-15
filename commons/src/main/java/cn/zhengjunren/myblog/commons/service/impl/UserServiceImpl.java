package cn.zhengjunren.myblog.commons.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.commons.mapper.UserMapper;
import cn.zhengjunren.myblog.commons.domain.User;
import cn.zhengjunren.myblog.commons.service.UserService;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
