package cn.zhengjunren.myblog.business.mapper;

import cn.zhengjunren.myblog.business.dto.TbUserWithRole;
import cn.zhengjunren.myblog.commons.domain.TbUser;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * <p>ClassName: TbUserMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:11
 */
//@Repository
public interface TbUserMapper extends MyMapper<TbUser> {

    /**
     * 获取所有带角色的用户信息
     * @return {@link List<TbUserWithRole>}
     */
    List<TbUserWithRole> selectAllWithRole();
}
