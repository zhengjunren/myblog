package cn.zhengjunren.myblog.business.mapper;

import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.commons.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.MyMapper;

/**
 * @author ZhengJunren
 */
@CacheNamespace(implementation = RedisCache.class)
public interface TbPermissionMapper extends MyMapper<TbPermission> {

}
