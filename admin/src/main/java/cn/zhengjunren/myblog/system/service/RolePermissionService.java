package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RolePermissionService extends IService<RolePermission>{

    /**
     * 根据角色权限查询
     * @param roleId 角色id
     * @return 角色拥有的权限
     */
    List<RolePermission> selectByRoleId(long roleId);

    /**
     * 更新角色权限
     * @param permissionIds 需要更新的权限
     * @param roleId 角色id
     * @return 影响条数
     */
    int updatePermission(List<Long> permissionIds, long roleId);
}
