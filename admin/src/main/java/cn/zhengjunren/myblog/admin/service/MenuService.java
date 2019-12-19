package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
     */
    int update(Menu menu);

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
