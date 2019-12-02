package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.security.domain.TbRole;

import java.util.List;

public interface TbRoleService{
    List<TbRole> getRoleByUsername(String username);

//    List<>
}
