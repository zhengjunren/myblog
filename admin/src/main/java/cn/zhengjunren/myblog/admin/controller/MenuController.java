package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import cn.zhengjunren.myblog.admin.service.MenuService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.admin.utils.SecurityUtil;
import cn.zhengjunren.myblog.admin.vo.UserPrincipal;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>ClassName: MenuController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/16 17:11
 */
@Slf4j
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
        List<Role> roles = userPrincipal.getRoles().stream().map(roleService::selectByName).collect(Collectors.toList());
        List<MenuDTO> menuDTOList = menuService.findByRoles(roles);
        List<MenuDTO> menuDTOS = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
        return ApiResponse.ofSuccess(menuService.buildMenus(menuDTOS));
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

    /**
     * 新增
     * @param menu {@link Menu}
     * @return 成功
     */
    @PostMapping
    public ApiResponse create(@RequestBody Menu menu) {
        if (menu.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        menuService.save(menu);
        return ApiResponse.ofSuccess();
    }

    /**
     * 根据 id 删除
     * @param id id
     * @return 成功
     */
    @DeleteMapping(value = "/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        List<Menu> menuList = menuService.findByParentId(id);
        Set<Menu> menuSet = new HashSet<>();
        menuSet.add(menuService.getById(id));
        menuSet = menuService.getDeleteMenus(menuList, menuSet);
        List<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toList());
        try {
            menuService.delete(ids);
        } catch (DataIntegrityViolationException e) {
            log.error("【异常捕获】DataIntegrityViolationException: 错误信息 {}", e.getMessage());
            return ApiResponse.ofStatus(Status.MENU_IS_ASSOCIATED_WITH_ROLE);
        }
        return ApiResponse.ofSuccess();
    }

    /**
     * 菜单树
     * @return 菜单树结果
     */
    @GetMapping(value = "/tree")
    public ApiResponse getMenuTree() {
        return ApiResponse.ofSuccess(menuService.getMenuTree(menuService.findByParentId(0L)));
    }

    /**
     * 更新菜单
     * @param resources 需要更新的菜单
     * @return 成功
     */
    @PutMapping
    public ApiResponse update(@Validated @RequestBody Menu resources){
        menuService.update(resources);
        return ApiResponse.ofSuccess();
    }
}
