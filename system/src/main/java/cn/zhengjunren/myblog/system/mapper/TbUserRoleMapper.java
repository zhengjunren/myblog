package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.TbUserRole;
import tk.mybatis.mapper.MyMapper;

/**
 * <p>ClassName: TbUserRoleMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/08 22:33
 */
public interface TbUserRoleMapper extends MyMapper<TbUserRole> {

    /**
     * 根据角色 id 查询是否有关联的用户
     * @param roleId 角色 id
     * @return 结果为 0 没有，否则都有
     */
    int isExisted(Long roleId);
}
