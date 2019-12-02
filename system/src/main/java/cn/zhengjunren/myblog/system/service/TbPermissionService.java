package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.RoleAndUrl;
import cn.zhengjunren.myblog.system.domain.TbPermission;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionService {

    /**
     * 根据角色获取角色所拥有的url
     * @return {@link List<RoleAndUrl>}
     */
    List<RoleAndUrl> getUrlWithRole();

    List<TbPermission> selectAll();
}
