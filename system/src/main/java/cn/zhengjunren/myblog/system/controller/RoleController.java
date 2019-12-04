package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.service.TbRoleService;
import org.springframework.web.bind.annotation.GetMapping;
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
@RestController
public class RoleController {

    private final TbRoleService tbRoleService;

    public RoleController(TbRoleService tbRoleService) {
        this.tbRoleService = tbRoleService;
    }

    @GetMapping
    public ResponseResult<List<TbRole>> list() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取角色",tbRoleService.selectAll());
    }
}
