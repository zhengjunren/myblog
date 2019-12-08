package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.commons.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * @author ZhengJunren
 */
@CacheNamespace(implementation = RedisCache.class)
public interface TbPermissionMapper extends MyMapper<TbPermission> {

    /**
     * 根据角色 id 获取角色所有的权限
     * @param roleId 角色 id
     * @return {@link List<TbPermission>}
     */
    List<TbPermission> selectByRoleId(@Param("roleId") Long roleId);

}
