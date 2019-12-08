package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>ClassName: PermissionController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/4 8:01
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("permission")
@Api(tags = "权限管理")
public class PermissionController {

    private final TbPermissionService tbPermissionService;


    public PermissionController(TbPermissionService tbPermissionService) {
        this.tbPermissionService = tbPermissionService;
    }


    @GetMapping
    @MyLog("获取树状权限信息")
    @ApiOperation(value = "获取树状权限信息")
    public ResponseResult<Object> getPermissions() {
        List<TbPermission> tbPermissions = tbPermissionService.selectByParentId(0L);
        Object permissionTree = tbPermissionService.getPermissionTree(tbPermissions);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取树形权限", permissionTree);
    }

    @GetMapping("{roleId}")
    @MyLog("通过角色获取权限")
    @ApiOperation(value = "根据角色Id获取权限")
    public ResponseResult<List<TbPermission>> getByRoleId(@PathVariable long roleId){
        List<TbPermission> tbPermissions = tbPermissionService.selectByRoleId(roleId);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "根据角色获取权限", tbPermissions);
    }

}
