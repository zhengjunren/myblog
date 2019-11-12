package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.business.domain.TbUser;

/**
 * <p>ClassName: TbUserService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:11
 */
public interface TbUserService{

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return {@link TbUser}
     */
    TbUser getByUsername(String username);

    /**
     * 插入
     * @param tbUser {@link TbUser}
     */
    int insert(TbUser tbUser);
}
