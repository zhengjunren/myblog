package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.commons.domain.TbPermission;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionService {

    /**
     * 获取所有权限
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectAll();

    /**
     * 获取权限树
     * @param permissions 权限
     * @return {@link Object}
     */
    Object getPermissionTree(List<TbPermission> permissions);


    /**
     * 根据父 id 获取权限列表
     * @param parentId 父权限 id
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectByParentId(long parentId);

    /**
     * 根据角色名获取角色所有的权限
     * @param roleEnName 角色名
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectByRoleEnName(String roleEnName);
}
