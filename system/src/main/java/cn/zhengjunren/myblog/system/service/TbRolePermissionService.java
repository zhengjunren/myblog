package cn.zhengjunren.myblog.system.service;

import java.util.List;

public interface TbRolePermissionService{
    /**
     * 根据角色 id 获取相应的权限 id
     * @param roleId 角色 id
     * @return 权限 id
     */
    List<Long> selectPermissionIdsByRoleId(Long roleId);

    /**
     * 更新
     * @param permissionIds 需要更新的id列表
     * @param roleId 角色id
     * @return 结果
     */
    int update(List<Long> permissionIds, Long roleId);

    /**
     * 根据角色 id 批量输出
     * @param permissionIds 权限 id
     * @param roleId 角色 id
     * @return 结果
     */
    int deleteBatch(List<Long> permissionIds, Long roleId);
}
