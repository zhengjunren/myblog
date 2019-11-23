package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.business.domain.RoleAndUrl;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionService{

    /**
     * 根据角色获取角色所拥有的url
     * @return {@link List<RoleAndUrl>}
     */
    List<RoleAndUrl> getUrlWithRole();
}
