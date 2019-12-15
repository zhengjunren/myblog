package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.security.domain.Permission;
import cn.zhengjunren.myblog.security.mapper.PermissionMapper;
import cn.zhengjunren.myblog.security.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ZhengJunren
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

}
