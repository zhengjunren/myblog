package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.zhengjunren.myblog.system.domain.Menu;
import cn.zhengjunren.myblog.system.domain.Role;
import cn.zhengjunren.myblog.system.dto.MenuDTO;
import cn.zhengjunren.myblog.system.mapper.MenuMapper;
import cn.zhengjunren.myblog.system.service.MenuService;
import cn.zhengjunren.myblog.system.vo.MenuMetaVo;
import cn.zhengjunren.myblog.system.vo.MenuVo;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.exception.EntityExistException;
import cn.zhengjunren.myblog.common.utils.ValidationUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = Consts.MENU_CACHE_NAME)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Override
    public List<Menu> findByParentId(Long parentId) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(Menu.COL_PARENT_ID, parentId);
        menuQueryWrapper.orderByAsc(Menu.COL_SORT);
        return menuMapper.selectList(menuQueryWrapper);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int update(Menu resources) {
        if(resources.getId().equals(resources.getParentId())) {
            throw new BadRequestException(400, "上级不能为自己");
        }
        Menu menu = menuMapper.selectById(resources.getId());
        if (menu == null) {
            menu = new Menu();
        }
        ValidationUtil.isNull(menu.getId(),"Permission","id",resources.getId());

        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith("http://")||resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException(400, "外链必须以http://或者https://开头");
            }
        }
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(Menu.COL_NAME, resources.getName());
        Menu menu1 = menuMapper.selectOne(menuQueryWrapper);

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new EntityExistException(Menu.class,"菜单标题",resources.getName());
        }

        if(StringUtils.isNotBlank(resources.getComponentName())){
            QueryWrapper<Menu> menuQueryWrapper2 = new QueryWrapper<>();
            menuQueryWrapper2.eq(Menu.COL_COMPONENT_NAME, resources.getComponentName());
            menu1 = menuMapper.selectOne(menuQueryWrapper2);
            if(menu1 != null && !menu1.getId().equals(menu.getId())){
                throw new EntityExistException(Menu.class,"组件名",resources.getComponentName());
            }
        }
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setParentId(resources.getParentId());
        menu.setSort(resources.getSort());
        menu.setCache(resources.getCache());
        menu.setHidden(resources.getHidden());
        menu.setComponentName(resources.getComponentName());
        menu.setPermission(resources.getPermission());
        menu.setType(resources.getType());
        return menuMapper.updateById(menu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public int delete(List<Long> ids) {
        return menuMapper.deleteBatchIds(ids);
    }

    @Override
    public Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();

        for (Menu menu1 : menuList) {
            menuSet.add(menu1);
            menuQueryWrapper.eq(Menu.COL_PARENT_ID, menu1.getId());
            List<Menu> menus = menuMapper.selectList(menuQueryWrapper);
            if(menus!=null && menus.size()!=0){
                getDeleteMenus(menus, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    public List<MenuDTO> getAll() {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.orderByAsc(Menu.COL_SORT);
        List<Menu> menus = menuMapper.selectList(menuQueryWrapper);
        return menuMapper.toDto(menus);
    }

    @Override
    public List<MenuDTO> findByRoles(List<Role> roles) {
        Set<Menu> menus = new LinkedHashSet<>();
        for (Role role : roles) {
            List<Menu> menus1 = new ArrayList<>(menuMapper.findByRolesIdAndTypeIsNotInOrderBySortAsc(role.getId(), 2));
            menus.addAll(menus1);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public Object getMenuTree(List<Menu> menus) {
        List<Map<String,Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu!=null){
                        List<Menu> menuList = findByParentId(menu.getId());
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",menu.getId());
                        map.put("label",menu.getName());
                        if(menuList!=null && menuList.size()!=0){
                            map.put("children",getMenuTree(menuList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public Map<String, Object> buildTree(List<MenuDTO> menuDTOS) {
        List<MenuDTO> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDTO menuDTO : menuDTOS) {
            if (menuDTO.getParentId() == 0) {
                trees.add(menuDTO);
            }
            for (MenuDTO it : menuDTOS) {
                if (it.getParentId().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = menuDTOS.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", trees);
        map.put("totalElements", menuDTOS.size());
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
            if (menuDTO != null) {
                List<MenuDTO> menuDTOList = menuDTO.getChildren();
                MenuVo menuVo = new MenuVo();
                menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getName());
                // 一级目录需要加斜杠，不然会报警告
                menuVo.setPath(menuDTO.getParentId() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                menuVo.setHidden(menuDTO.getHidden());
                // 如果不是外链
                if (!menuDTO.getIFrame()) {
                    if (menuDTO.getParentId() == 0) {
                        menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                    } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                        menuVo.setComponent(menuDTO.getComponent());
                    }
                }
                menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon(), !menuDTO.getCache()));
                if (menuDTOList != null && menuDTOList.size() != 0) {
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                    menuVo.setChildren(buildMenus(menuDTOList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if (menuDTO.getParentId() == 0) {
                    MenuVo menuVo1 = new MenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if (!menuDTO.getIFrame()) {
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menuDTO.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<MenuVo> list1 = new ArrayList<>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        });
        return list;
    }
}
