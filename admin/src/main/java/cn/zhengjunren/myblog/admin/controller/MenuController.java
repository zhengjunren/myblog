package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.common.ApiResponse;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import cn.zhengjunren.myblog.admin.service.MenuService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.admin.utils.SecurityUtil;
import cn.zhengjunren.myblog.admin.vo.UserPrincipal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>ClassName: MenuController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/16 17:11
 */
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    private final RoleService roleService;

    public MenuController(MenuService menuService, RoleService roleService) {
        this.menuService = menuService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/build")
    public ApiResponse build() {
        UserPrincipal userPrincipal = SecurityUtil.getCurrentUser();
        assert userPrincipal != null;
        List<String> rolesStrs = userPrincipal.getRoles();
        List<Role> roles = rolesStrs.stream().map(rolesStr -> roleService.getOne(new QueryWrapper<Role>().eq(Role.COL_NAME, rolesStr)))
                .collect(Collectors.toList());
        List<MenuDTO> menuDTOList = menuService.findByRoles(roles);
        List<MenuDTO> menuDTOS = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
        return ApiResponse.ofSuccess(menuService.buildMenus(menuDTOS));
    }
}
