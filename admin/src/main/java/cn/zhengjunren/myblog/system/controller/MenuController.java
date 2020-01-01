package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.common.utils.DataTypeUtils;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.security.utils.SecurityUtil;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import cn.zhengjunren.myblog.system.domain.Menu;
import cn.zhengjunren.myblog.system.domain.Role;
import cn.zhengjunren.myblog.system.dto.MenuDTO;
import cn.zhengjunren.myblog.system.service.MenuService;
import cn.zhengjunren.myblog.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(tags = "菜单管理")
public class MenuController extends BaseController<Menu, MenuService, BaseQueryPageCondition> {

    private final RoleService roleService;

    private final static String ENTITY_NAME = "menu";

    public MenuController(MenuService service, RoleService roleService) {
        super(service);
        this.roleService = roleService;
    }


    /**
     * 根据角色生成前端所需的菜单
     * @return 菜单
     */
    @MyLog("获取前端所需菜单")
    @GetMapping(value = "/build")
    @ApiOperation(value = "获取前端所需菜单")
    public ApiResponse build() {
        UserPrincipal userPrincipal = SecurityUtil.getCurrentUser();
        assert userPrincipal != null;
        List<Role> roles = userPrincipal.getRoles().stream().map(roleService::selectByName).collect(Collectors.toList());
        List<MenuDTO> menuDTOList = service.findByRoles(roles);
        List<MenuDTO> menuDTOS = (List<MenuDTO>) service.buildTree(menuDTOList).get("content");
        return ApiResponse.ofSuccess(service.buildMenus(menuDTOS));
    }

    /**
     * 获取树形菜单列表
     * @return 树形菜单列表
     */
    @GetMapping("list")
    @ApiOperation(value = "获取树形菜单列表")
    public ApiResponse getMenus() {
        List<MenuDTO> list = service.getAll();
        return ApiResponse.ofSuccess(service.buildTree(list));
    }

    /**
     * 根据 id 删除
     * @param id id
     * @return 成功
     */
    @DeleteMapping(value = "/{id}")
    @MyLog("删除菜单")
    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.PATH)
    public ApiResponse delete(@PathVariable Long id) {
        List<Menu> menuList = service.findByParentId(id);
        Set<Menu> menuSet = new HashSet<>();
        menuSet.add(service.getById(id));
        menuSet = service.getDeleteMenus(menuList, menuSet);
        List<Long> ids = menuSet.stream().map(Menu::getId).collect(Collectors.toList());
        try {
            service.delete(ids);
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
    @ApiOperation(value = "菜单树")
    @GetMapping(value = "/tree")
    public ApiResponse getMenuTree() {
        return ApiResponse.ofSuccess(service.getMenuTree(service.findByParentId(0L)));
    }

    /**
     * 更新菜单
     * @param resources 需要更新的菜单
     * @return 成功
     */
    @PutMapping
    @MyLog("更新菜单")
    @ApiOperation(value = "更新菜单")
    @ApiImplicitParam(name = "resources", value = "", required = true, dataType = "Menu", paramType = ParamTypeUtils.BODY)
    public ApiResponse update(@Validated @RequestBody Menu resources){
        service.update(resources);
        return ApiResponse.ofSuccess();
    }

}
