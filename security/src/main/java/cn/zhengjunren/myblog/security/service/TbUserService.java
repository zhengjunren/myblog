package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.commons.domain.TbUser;

public interface TbUserService{

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return {@link TbUser}
     */
    TbUser getByUsername(String username);
}
