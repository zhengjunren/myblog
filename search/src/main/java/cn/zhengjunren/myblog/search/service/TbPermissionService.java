package cn.zhengjunren.myblog.search.service;

import cn.zhengjunren.myblog.search.domain.RoleAndUrl;

import java.util.List;

public interface TbPermissionService {
    List<RoleAndUrl> getUrlWithRole();
}
