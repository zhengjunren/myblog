package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.TbRole;

import java.util.List;

public interface TbRoleService{

    /**
     * 获取所有角色
     * @return {@link List<TbRole>}
     */
    List<TbRole> selectAll();
}
