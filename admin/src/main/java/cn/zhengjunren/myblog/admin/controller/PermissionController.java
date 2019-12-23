package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.Permission;
import cn.zhengjunren.myblog.admin.service.PermissionService;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>ClassName: PermissionController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 23:50
 */
@RestController
@RequestMapping("/api/permissions")
@Api(tags = "权限管理")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @MyLog("获取权限树")
    @GetMapping("tree")
    @ApiOperation(value = "获取权限树")
    public ApiResponse getPermissionTree() {
        List<Permission> permissions = permissionService.selectByParentId(0L);
        Object permissionTree = permissionService.getPermissionTree(permissions);
        return ApiResponse.ofSuccess(permissionTree);
    }
}
