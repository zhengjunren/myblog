package cn.zhengjunren.myblog.admin.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.admin.domain.User;
import cn.zhengjunren.myblog.admin.mapper.UserMapper;
import cn.zhengjunren.myblog.admin.service.UserService;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}
