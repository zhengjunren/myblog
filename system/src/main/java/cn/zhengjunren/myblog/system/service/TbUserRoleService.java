package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.TbUserRole;

/**
 * <p>ClassName: TbUserRoleService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/08 22:33
 */
public interface TbUserRoleService{

    /**
     * 更新
     * @param tbUserRole {@link TbUserRole}
     * @return 结果大于 0 成功，否则失败
     */
    int update(TbUserRole tbUserRole);

    /**
     * 给用户新增权限
     * @param tbUserRole {@link TbUserRole}
     * @return 结果大于 0 成功，否则失败
     */
    int insert(TbUserRole tbUserRole);

    /**
     * 根据角色 id 查询是否有关联的用户
     * @param roleId 角色 id
     * @return true 存在
     */
    boolean isExisted(Long roleId);
}
