package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.business.domain.TbUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

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
     *  插入
     * @param tbUser {@link TbUser}
     * @return 返回是否插入成功
     */
    int insert(TbUser tbUser);

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页显示的数量
     * @return {@link List <TbUser>}
     */
    public PageInfo<TbUser> page(Integer pageNum, Integer pageSize);

    /**
     * 管理员更新用户信息
     * @param tbUser {@link TbUser}
     * @return 是否更新成功
     */
    public int update(TbUser tbUser);

    /**
     * 更新个人信息
     * @param oldTbUser 原用户信息
     * @param newTbUser 用户修改后的信息
     * @return 是否更新成功
     */
    public int modifyProfile(TbUser oldTbUser, TbUser newTbUser);
}
