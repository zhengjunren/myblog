package cn.zhengjunren.myblog.security.mapper;

import cn.zhengjunren.myblog.security.domain.TbPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbPermissionMapper extends MyMapper<TbPermission> {
    List<TbPermission> selectByRole(@Param("roleName") String roleName);
}
