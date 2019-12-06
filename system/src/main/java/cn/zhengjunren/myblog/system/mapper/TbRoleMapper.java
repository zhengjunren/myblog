package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.commons.utils.RedisCache;
import cn.zhengjunren.myblog.system.domain.TbRole;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbRoleMapper extends MyMapper<TbRole> {
}
