package cn.zhengjunren.myblog.admin.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.admin.domain.RoleMenu;
import cn.zhengjunren.myblog.admin.mapper.RoleMenuMapper;
import cn.zhengjunren.myblog.admin.service.RoleMenuService;
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService{

}
