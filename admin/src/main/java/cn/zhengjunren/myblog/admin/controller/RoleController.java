package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.common.BaseController;
import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.params.MenuParams;
import cn.zhengjunren.myblog.admin.dto.params.PermissionParams;
import cn.zhengjunren.myblog.admin.service.RoleMenuService;
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

    @PutMapping("menu")
    public ApiResponse updateMenu(@RequestBody MenuParams menuParams) {
        roleMenuService.updateMenu(menuParams.getMenuIds(), menuParams.getCurrentRoleId());
        return ApiResponse.ofSuccess();
    }


//    @Override
//    @GetMapping("excel")
//    public void exportExcel(HttpServletResponse response) throws IOException {
//        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
//        response.setContentType("application/vnd.ms-excel");
//        response.setCharacterEncoding("utf-8");
//        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//        String fileName = URLEncoder.encode("角色", "UTF-8");
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), Role.class).sheet("sheet1").doWrite(service.list());
//    }

}
