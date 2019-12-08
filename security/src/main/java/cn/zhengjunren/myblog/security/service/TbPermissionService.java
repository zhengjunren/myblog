package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.commons.domain.TbPermission;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionService{
    /**
     * 通过角色名获取权限
     * @param roleName 角色名
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectByRole(String roleName);
}
