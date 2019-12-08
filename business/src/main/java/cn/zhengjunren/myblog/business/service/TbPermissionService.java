package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.commons.domain.TbPermission;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionService{

    /**
     * 获取所有权限
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectAll();
}
