package cn.zhengjunren.myblog.system.service;

import java.util.List;

public interface TbRolePermissionService{
    /**
     * 根据角色名获取相应的权限 id
     * @param roleEnName 角色名
     * @return 权限 id
     */
    List<Long> selectPermissionIdsByRoleEnName(String roleEnName);

    /**
     * 更新
     * @param permissionIds 需要更新的id列表
     * @param roleEnName 角色名
     * @return 结果
     */
    int update(List<Long> permissionIds, String roleEnName);

    /**
     * 根据角色 id 批量输出
     * @param permissionIds 权限 id
     * @param roleId 角色 id
     * @return 结果
     */
    int deleteBatch(List<Long> permissionIds, Long roleId);



}
