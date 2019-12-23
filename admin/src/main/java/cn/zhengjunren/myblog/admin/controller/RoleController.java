package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.params.MenuParams;
import cn.zhengjunren.myblog.admin.dto.params.PermissionParams;
import cn.zhengjunren.myblog.admin.service.RoleMenuService;
import cn.zhengjunren.myblog.admin.service.RolePermissionService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: RoleController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 11:58
 */
@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController<Role, RoleService> {

    private final RolePermissionService rolePermissionService;

    private final RoleMenuService roleMenuService;

    public RoleController(RoleService service, RolePermissionService rolePermissionService, RoleMenuService roleMenuService) {
        super(service);
        this.rolePermissionService = rolePermissionService;
        this.roleMenuService = roleMenuService;
    }


    /**
     *  分页查询
     * @param page 页码
     * @param limit 每页显示条数
     * @return 分页结果
     */
    @Override
    @MyLog("分页查询角色")
    public ApiResponse page(long page, long limit) {
        return ApiResponse.ofSuccess(service.page(page, limit));
    }

    /**
     * 更新
     * @param role 需要更新的角色
     * @return 更新结果
     */
    @PutMapping
    @MyLog("更新角色")
    public ApiResponse update(@RequestBody Role role) {
        service.updateRole(role);
        return ApiResponse.ofSuccess();
    }

    @PutMapping("permission")
    @MyLog("更新角色权限")
    public ApiResponse updatePermission(@RequestBody PermissionParams permissionParams) {
        rolePermissionService.updatePermission(permissionParams.getPermissionIds(), permissionParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }

    @PutMapping("menu")
    @MyLog("更新角色菜单")
    public ApiResponse updateMenu(@RequestBody MenuParams menuParams) {
        roleMenuService.updateMenu(menuParams.getMenuIds(), menuParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping("{id}")
    @MyLog("删除角色")
    public ApiResponse delete(@PathVariable long id) {
        try {
            service.removeById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("【异常捕获】DataIntegrityViolationException: 错误信息 {}", e.getMessage());
            return ApiResponse.ofStatus(Status.MENU_IS_ASSOCIATED_WITH_ROLE);
        }
        return ApiResponse.ofSuccess();
    }
}
