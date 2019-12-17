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

    List<MenuDTO> findByRoles(List<Role> roles);

    Map<String,Object> buildTree(List<MenuDTO> menuDTOS);

    Object buildMenus(List<MenuDTO> byRoles);
}
