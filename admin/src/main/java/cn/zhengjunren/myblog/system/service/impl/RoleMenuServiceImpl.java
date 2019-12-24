package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.RoleMenu;
import cn.zhengjunren.myblog.system.mapper.RoleMenuMapper;
import cn.zhengjunren.myblog.system.service.RoleMenuService;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.exception.BaseException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {Consts.MENU_CACHE_NAME})
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService{

    @Override
    @CacheEvict(allEntries = true)
    public int updateMenu(List<Long> menuIds, long roleId) {
        List<Long> oldMenuIds = baseMapper.selectByRoleId(roleId).stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        HashSet<Long> oldMenu = new HashSet<>(oldMenuIds);
        HashSet<Long> newMenu = new HashSet<>(menuIds);
        if (menuIds.size() < oldMenuIds.size()){
            oldMenu.removeAll(newMenu);
            List<Long> list = new ArrayList<>(oldMenu);
            baseMapper.deleteBatch(list, roleId);
        }
        // 新增
        else {
            newMenu.removeAll(oldMenu);
            List<RoleMenu> roleMenus = new ArrayList<>();
            if (newMenu.isEmpty()) {
                throw new BaseException(400, "菜单修改无效！");
            }
            for (Long aLong : newMenu) {
                roleMenus.add(new RoleMenu(aLong, roleId));
            }
            saveBatch(roleMenus);
        }
        return 0;
    }
}
