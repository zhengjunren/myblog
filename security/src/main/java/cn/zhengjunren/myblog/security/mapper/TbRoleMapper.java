package cn.zhengjunren.myblog.security.mapper;

import cn.zhengjunren.myblog.security.domain.TbRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbRoleMapper extends MyMapper<TbRole> {

    List<TbRole> getRoleByUsername(@Param("username") String username);
}
