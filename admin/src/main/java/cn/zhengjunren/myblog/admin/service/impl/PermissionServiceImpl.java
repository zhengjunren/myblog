package cn.zhengjunren.myblog.admin.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.admin.domain.Permission;
import cn.zhengjunren.myblog.admin.mapper.PermissionMapper;
import cn.zhengjunren.myblog.admin.service.PermissionService;
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

}
