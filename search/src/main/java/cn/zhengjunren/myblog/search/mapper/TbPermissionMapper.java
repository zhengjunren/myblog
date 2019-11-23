package cn.zhengjunren.myblog.search.mapper;

import cn.zhengjunren.myblog.search.domain.RoleAndUrl;
import cn.zhengjunren.myblog.search.domain.TbPermission;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbPermissionMapper extends MyMapper<TbPermission> {
    List<RoleAndUrl> getUrlWithRole();
}
