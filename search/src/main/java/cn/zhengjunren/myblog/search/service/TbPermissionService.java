package cn.zhengjunren.myblog.search.service;

import cn.zhengjunren.myblog.search.domain.RoleAndUrl;
import cn.zhengjunren.myblog.search.domain.TbPermission;

import java.util.List;

public interface TbPermissionService {
    List<RoleAndUrl> getUrlWithRole();

    List<TbPermission> selectAll();
}
