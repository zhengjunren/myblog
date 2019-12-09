package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.utils.DataTypeUtils;
import cn.zhengjunren.myblog.commons.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.domain.TbUserRole;
import cn.zhengjunren.myblog.system.dto.PermissionParams;
import cn.zhengjunren.myblog.system.service.TbRolePermissionService;
import cn.zhengjunren.myblog.system.service.TbRoleService;
import cn.zhengjunren.myblog.system.service.TbUserRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>ClassName: RoleController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/4 10:07
 */
@RequestMapping("role")
@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@Api(tags = "角色管理")
public class RoleController {

    private final TbRoleService tbRoleService;

    private final TbRolePermissionService tbRolePermissionService;

    private final TbUserRoleService tbUserRoleService;

    public RoleController(TbRoleService tbRoleService, TbRolePermissionService tbRolePermissionService, TbUserRoleService tbUserRoleService) {
        this.tbRoleService = tbRoleService;
        this.tbRolePermissionService = tbRolePermissionService;
        this.tbUserRoleService = tbUserRoleService;
    }

    @GetMapping("list")
    @ApiOperation(value = "获取角色列表", notes="根据页码、笔数查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
    })
    @MyLog("获取角色列表")
    public ResponseResult<ListInfo<TbRole>> page(int page, int limit) {
        PageInfo<TbRole> pageInfo = tbRoleService.page(page, limit);
        ListInfo<TbRole> listInfo = new ListInfo<>();
        listInfo.setItems(pageInfo.getList());
        listInfo.setTotal(pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色",listInfo);
    }

    @PutMapping("permission")
    @ApiOperation(value = "更新权限")
    @MyLog("更新权限")
    @ApiImplicitParam(name = "permissionParams", value = "携带的权限ids和角色id", required = true, dataType = "PermissionParams", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> updatePermission(@RequestBody PermissionParams permissionParams){
        int result = tbRolePermissionService.update(permissionParams.getPermissionIds(), permissionParams.getCurrentRoleEnName());
        if (result == 2){
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "请您做出权限的修改好吗，不然不要乱点，查数据库很烦！");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新权限成功，拥有该角色的用户重新登录后生效");
    }

    @GetMapping("all")
    @ApiOperation(value = "获取所有角色")
    public ResponseResult<List<TbRole>> getAllRole() {
        List<TbRole> tbRoles = tbRoleService.selectAll();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取所有角色", tbRoles);
    }

    @PutMapping("user")
    public ResponseResult<Void> modifyUserRole(@RequestBody TbUserRole tbUserRole) {
        tbUserRoleService.update(tbUserRole);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新用户角色成功");
    }

    @PostMapping
    public ResponseResult<Void> add(@RequestBody TbRole tbRole) {
        tbRoleService.insert(tbRole);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "新增角色成功");
    }

    @PutMapping
    public ResponseResult<Void> update(@RequestBody TbRole tbRole) {
        tbRoleService.update(tbRole);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新角色成功");
    }
}
