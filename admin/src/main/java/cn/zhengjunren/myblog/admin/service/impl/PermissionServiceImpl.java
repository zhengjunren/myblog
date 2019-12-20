package cn.zhengjunren.myblog.admin.service.impl;

import cn.zhengjunren.myblog.admin.domain.Permission;
import cn.zhengjunren.myblog.admin.mapper.PermissionMapper;
import cn.zhengjunren.myblog.admin.service.PermissionService;
import cn.zhengjunren.myblog.common.consts.Consts;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = Consts.PERMISSION_CACHE_NAME)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService{

    @Override
    @Cacheable(key = "#p0")
    public List<Permission> selectByRoleIdList(List<Long> ids) {
        return baseMapper.selectByRoleIdList(ids);
    }
}
