package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.common.utils.DataTypeUtils;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.system.domain.Role;
import cn.zhengjunren.myblog.system.dto.params.MenuParams;
import cn.zhengjunren.myblog.system.dto.params.PermissionParams;
import cn.zhengjunren.myblog.system.service.RoleMenuService;
import cn.zhengjunren.myblog.system.service.RolePermissionService;
import cn.zhengjunren.myblog.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "角色管理")
public class RoleController extends BaseController<Role, RoleService, BaseQueryPageCondition> {

    private final RolePermissionService rolePermissionService;

    private final RoleMenuService roleMenuService;

    public RoleController(RoleService service, RolePermissionService rolePermissionService, RoleMenuService roleMenuService) {
        super(service);
        this.rolePermissionService = rolePermissionService;
        this.roleMenuService = roleMenuService;
    }


    /**
     *  分页查询
     * @param baseQueryPageCondition 分页查询条件
     * @return 分页结果
     */
    @Override
    @MyLog("分页查询角色")
    @ApiOperation(value = "查询角色", notes="分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.QUERY),
    })
    public ApiResponse page(BaseQueryPageCondition baseQueryPageCondition) {
        return ApiResponse.ofSuccess(service.page(baseQueryPageCondition.getPage(), baseQueryPageCondition.getLimit()));
    }

    /**
     * 更新
     * @param role 需要更新的角色
     * @return 更新结果
     */
    @PutMapping
    @MyLog("更新角色")
    @ApiOperation(value = "更新角色")
    @ApiImplicitParam(name = "role", value = "更新角色", required = true, dataType = "Role", paramType = ParamTypeUtils.BODY)
    public ApiResponse update(@RequestBody Role role) {
        service.updateRole(role);
        return ApiResponse.ofSuccess();
    }

    @PutMapping("permission")
    @MyLog("更新角色权限")
    @ApiOperation(value = "更新角色权限")
    @ApiImplicitParam(name = "permissionParams", value = "角色id和权限id", required = true, dataType = "PermissionParams", paramType = ParamTypeUtils.BODY)
    public ApiResponse updatePermission(@RequestBody PermissionParams permissionParams) {
        rolePermissionService.updatePermission(permissionParams.getPermissionIds(), permissionParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }

    @PutMapping("menu")
    @MyLog("更新角色菜单")
    @ApiOperation(value = "更新角色菜单")
    @ApiImplicitParam(name = "menuParams", value = "角色id和菜单id", required = true, dataType = "MenuParams", paramType = ParamTypeUtils.BODY)
    public ApiResponse updateMenu(@RequestBody MenuParams menuParams) {
        roleMenuService.updateMenu(menuParams.getMenuIds(), menuParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }

    @DeleteMapping("{id}")
    @MyLog("删除角色")
    @ApiOperation(value = "删除角色")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.PATH)
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
