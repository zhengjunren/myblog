package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.Permission;
import cn.zhengjunren.myblog.system.mapper.PermissionMapper;
import cn.zhengjunren.myblog.system.service.PermissionService;
import cn.zhengjunren.myblog.common.consts.Consts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Permission> selectByParentId(long parentId) {
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq(Permission.COL_PARENT_ID, parentId);
        return baseMapper.selectList(permissionQueryWrapper);
    }

    @Override
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
            if (permission!=null){
                List<Permission> tbPermissions = selectByParentId(permission.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",permission.getId());
                map.put("label",permission.getName());
                if(tbPermissions != null && tbPermissions.size() != 0){
                    map.put("children",getPermissionTree(tbPermissions));
                }
                list.add(map);
            }
        });
        return list;
    }
}
