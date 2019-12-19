package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import cn.zhengjunren.myblog.admin.exception.BadRequestException;
import cn.zhengjunren.myblog.admin.service.MenuService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.admin.utils.SecurityUtil;
import cn.zhengjunren.myblog.admin.vo.UserPrincipal;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final static String ENTITY_NAME = "menu";

    public MenuController(MenuService menuService, RoleService roleService) {
        this.menuService = menuService;
        this.roleService = roleService;
    }

    /**
     * 根据角色生成前端所需的菜单
     * @return 菜单
     */
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

    @GetMapping(value = "/tree")
    public ApiResponse getMenuTree() {
        return ApiResponse.ofSuccess(menuService.getMenuTree(menuService.findByParentId(0L)));
    }

    @PutMapping
    public ApiResponse update(@Validated @RequestBody Menu resources){
        menuService.update(resources);
        return ApiResponse.ofSuccess();
    }

    /**
     * 获取树形菜单列表
     * @return 树形菜单列表
     */
    @GetMapping
    public ApiResponse getMenus() {
        List<MenuDTO> list = menuService.getAll();
        return ApiResponse.ofSuccess(menuService.buildTree(list));
    }

    @PostMapping
    public ApiResponse create(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        menuService.save(menu);
        return ApiResponse.ofSuccess();
    }
}
