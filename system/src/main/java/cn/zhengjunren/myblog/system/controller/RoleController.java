package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.dto.PermissionParams;
import cn.zhengjunren.myblog.system.service.TbRolePermissionService;
import cn.zhengjunren.myblog.system.service.TbRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @date 2019/12/4 10:07
 */
@RequestMapping("role")
@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
public class RoleController {

    private final TbRoleService tbRoleService;

    private final TbRolePermissionService tbRolePermissionService;

    public RoleController(TbRoleService tbRoleService, TbRolePermissionService tbRolePermissionService) {
        this.tbRoleService = tbRoleService;
        this.tbRolePermissionService = tbRolePermissionService;
    }

    @GetMapping
    public ResponseResult<ListInfo<TbRole>> page(Integer page, Integer limit) {
        PageInfo<TbRole> pageInfo = tbRoleService.page(page, limit);
        ListInfo<TbRole> listInfo = new ListInfo<>();
        listInfo.setItems(pageInfo.getList());
        listInfo.setTotal(pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色",listInfo);
    }

    @PutMapping("permission")
    public ResponseResult<Void> updatePermission(@RequestBody PermissionParams permissionParams){
        int result = tbRolePermissionService.update(permissionParams.getPermissionIds(), permissionParams.getCurrentRoleId());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新权限成功");
    }

}
