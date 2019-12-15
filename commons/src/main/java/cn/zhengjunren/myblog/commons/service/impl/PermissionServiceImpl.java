package cn.zhengjunren.myblog.commons.service.impl;

import cn.zhengjunren.myblog.commons.domain.Permission;
import cn.zhengjunren.myblog.commons.mapper.PermissionMapper;
import cn.zhengjunren.myblog.commons.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

}
