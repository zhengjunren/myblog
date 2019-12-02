package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.security.domain.TbPermission;

import java.util.List;

public interface TbPermissionService{
    List<TbPermission> selectByRole(String roleName);
}
