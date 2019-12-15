package cn.zhengjunren.myblog.commons.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.commons.domain.Role;
import cn.zhengjunren.myblog.commons.mapper.RoleMapper;
import cn.zhengjunren.myblog.commons.service.RoleService;
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

}
