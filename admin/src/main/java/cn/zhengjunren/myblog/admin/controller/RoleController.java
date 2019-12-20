package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.common.BaseController;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.service.RoleService;
import cn.zhengjunren.myblog.common.result.ApiResponse;
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

    public RoleController(RoleService service) {
        super(service);
    }


    @Override
    public ApiResponse page(long current, long size) {
        return ApiResponse.ofSuccess(service.page(current, size));
    }
}
