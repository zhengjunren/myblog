package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.business.domain.TbUserRole;

public interface TbUserRoleService{

    /**
     * 更新
     * @param tbUserRole {@link TbUserRole}
     * @return 结果
     */
    int update(TbUserRole tbUserRole);
}
