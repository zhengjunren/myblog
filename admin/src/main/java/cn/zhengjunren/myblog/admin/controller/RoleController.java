package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.common.BaseController;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.params.PermissionParams;
import cn.zhengjunren.myblog.admin.service.RolePermissionService;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.common.result.ApiResponse;
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
@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController<Role, RoleService> {

    private final RolePermissionService rolePermissionService;

    public RoleController(RoleService service, RolePermissionService rolePermissionService) {
        super(service);
        this.rolePermissionService = rolePermissionService;
    }


    /**
     *  分页查询
     * @param page 页码
     * @param limit 每页显示条数
     * @return 分页结果
     */
    @Override
    public ApiResponse page(long page, long limit) {
        return ApiResponse.ofSuccess(service.page(page, limit));
    }

    /**
     * 更新
     * @param role 需要更新的角色
     * @return 更新结果
     */
    @PutMapping
    public ApiResponse update(@RequestBody Role role) {
        service.updateRole(role);
        return ApiResponse.ofSuccess();
    }

    @PutMapping("permission")
    public ApiResponse updatePermission(@RequestBody PermissionParams permissionParams) {
        rolePermissionService.updatePermission(permissionParams.getPermissionIds(), permissionParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }

}
