package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.TbPermission;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * @date 2019/12/4 8:01
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("permission")
public class PermissionController {

    private final TbPermissionService tbPermissionService;


    public PermissionController(TbPermissionService tbPermissionService) {
        this.tbPermissionService = tbPermissionService;
    }


    @GetMapping
    public ResponseResult<Object> getPermissions() {
        List<TbPermission> tbPermissions = tbPermissionService.selectByParentId(0L);
        Object permissionTree = tbPermissionService.getPermissionTree(tbPermissions);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取树形权限", permissionTree);
    }

}
