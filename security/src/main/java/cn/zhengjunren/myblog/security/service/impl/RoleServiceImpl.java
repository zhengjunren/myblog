package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.security.domain.Role;
import cn.zhengjunren.myblog.security.mapper.RoleMapper;
import cn.zhengjunren.myblog.security.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ZhengJunren
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

}
