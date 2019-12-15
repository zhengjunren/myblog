package cn.zhengjunren.myblog.admin.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.mapper.RoleMapper;
import cn.zhengjunren.myblog.admin.service.RoleService;
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

}
