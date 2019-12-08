package cn.zhengjunren.myblog.security.mapper;

import cn.zhengjunren.myblog.commons.utils.RedisCache;
import cn.zhengjunren.myblog.commons.domain.TbPermission;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * @author zhengjunren
 */
@CacheNamespace(implementation = RedisCache.class)
public interface TbPermissionMapper extends MyMapper<TbPermission> {

    /**
     * 根据角色获取权限
     * @param roleName 角色名
     * @return {@link}
     */
    List<TbPermission> selectByRole(@Param("roleName") String roleName);
}
