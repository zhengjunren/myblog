package cn.zhengjunren.myblog.business.mapper;

import cn.zhengjunren.myblog.business.domain.RoleAndUrl;
import cn.zhengjunren.myblog.business.domain.TbPermission;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface TbPermissionMapper extends MyMapper<TbPermission> {

    /**
     * 获取角色和角色所对应的url
     * @return {@link List<RoleAndUrl>}
     */
    List<RoleAndUrl> getUrlWithRole();
}
