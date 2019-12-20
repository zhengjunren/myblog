package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ZhengJunren
 */
public interface MenuService extends IService<Menu>{

    /**
     * 根据父 id 查询
     * @param parentId 父 id
     * @return {@link List<Menu>}
     */
    List<Menu> findByParentId(Long parentId);

    /**
     * 更新
     * @param menu {@link Menu}
     * @return 结果
     */
    int update(Menu menu);

    /**
     * 删除
     * @param ids
     */
    int delete(List<Long> ids);

    /**
     * 递归找出待删除的菜单
     * @param menuList
     * @param menuSet
     * @return
     */
    Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 获取所有的菜单数据
     * @return {@link List<MenuDTO>}
     */
    List<MenuDTO> getAll();

    /**
     * 通过角色获取菜单
     * @param roles 角色
     * @return {@link List<MenuDTO>}
     */
    List<MenuDTO> findByRoles(List<Role> roles);

    /**
     * 根据角色 id 和类型查找菜单
     * @param id 角色 id
     * @param type 类型
     * @return {@link List<Menu>}
     */
    List<Menu> findByRolesIdAndTypeIsNotInOrderBySortAsc(Long id, Integer type);

    /**
     * 获取菜单树
     * @param menus {@link List<Menu>}
     * @return 结果
     */
    Object getMenuTree(List<Menu> menus);

    /**
     * 构建菜单树
     * @param menuDTOS {@link List<MenuDTO>}
     * @return 结果集
     */
    Map<String,Object> buildTree(List<MenuDTO> menuDTOS);

    /**
     * 构建前端所需的菜单
     * @param menuDTOS {@link List<MenuDTO>}
     * @return 菜单集
     */
    Object buildMenus(List<MenuDTO> menuDTOS);
}
