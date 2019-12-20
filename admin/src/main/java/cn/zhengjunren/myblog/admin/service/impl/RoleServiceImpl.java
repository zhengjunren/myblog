package cn.zhengjunren.myblog.admin.service.impl;

import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.ListInfo;
import cn.zhengjunren.myblog.admin.mapper.PermissionMapper;
import cn.zhengjunren.myblog.admin.mapper.RoleMapper;
import cn.zhengjunren.myblog.admin.service.MenuService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.admin.vo.RoleVo;
import cn.zhengjunren.myblog.common.consts.Consts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = Consts.ROLE_CACHE_NAME)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    private final MenuService menuService;

    private final RoleMapper roleMapper;

    private final PermissionMapper permissionMapper;

    public RoleServiceImpl(MenuService menuService, RoleMapper roleMapper, PermissionMapper permissionMapper) {
        this.menuService = menuService;
        this.roleMapper = roleMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Role selectByName(String name) {
        return getOne(new QueryWrapper<Role>().eq(Role.COL_NAME, name));
    }

    @Override
    public ListInfo page(long current, long size) {
        Page<Role> tPage = new Page<>();
        tPage.setCurrent(current);
        tPage.setSize(size);
        IPage<Role> page = page(tPage);
        List<RoleVo> roleVos = page.getRecords().stream().map(role -> {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVo.setMenus(menuService.findByRolesIdAndTypeIsNotInOrderBySortAsc(role.getId(), 2));
            roleVo.setPermissions(permissionMapper.slectByRoleId(role.getId()));
            return roleVo;
        }).collect(Collectors.toList());
        ListInfo listInfo = new ListInfo();
        listInfo.setItems(roleVos);
        listInfo.setTotal(page.getSize());
        return listInfo;
    }

    @Override
    @Cacheable(value = "role")
    public List<Role> selectByUserId(Long userId) {
        return roleMapper.selectByUserId(userId);
    }
}
