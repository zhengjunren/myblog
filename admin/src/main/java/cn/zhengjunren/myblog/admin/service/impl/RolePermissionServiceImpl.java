package cn.zhengjunren.myblog.admin.service.impl;

import cn.zhengjunren.myblog.admin.domain.RolePermission;
import cn.zhengjunren.myblog.admin.mapper.RolePermissionMapper;
import cn.zhengjunren.myblog.admin.service.RolePermissionService;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.exception.BaseException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhengJunren
 */
@Service
@CacheConfig(cacheNames = {Consts.PERMISSION_CACHE_NAME})
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService{

    @Override
    public List<RolePermission> selectByRoleId(long roleId) {
        return baseMapper.selectByRoleId(roleId);

    }

    @Override
    @CacheEvict(allEntries = true)
    public int updatePermission(List<Long> permissionIds, long roleId) {
        List<Long> oldPermissionIds = baseMapper.selectByRoleId(roleId).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        HashSet<Long> oldPermission = new HashSet<>(oldPermissionIds);
        HashSet<Long> newPermission = new HashSet<>(permissionIds);
        //删除权限
        if (permissionIds.size() < oldPermissionIds.size()){
            oldPermission.removeAll(newPermission);
            List<Long> list = new ArrayList<>(oldPermission);
            baseMapper.deleteBatch(list, roleId);
        }
        // 新增
        else {
            newPermission.removeAll(oldPermission);
            List<RolePermission> rolePermissions = new ArrayList<>();
            if (newPermission.isEmpty()) {
                throw new BaseException(400, "权限修改无效！");
            }
            for (Long aLong : newPermission) {
                rolePermissions.add(new RolePermission(roleId, aLong));
            }
            saveBatch(rolePermissions);
        }
        return 0;
    }
}
