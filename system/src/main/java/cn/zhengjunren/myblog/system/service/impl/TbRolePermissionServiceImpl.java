package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbRolePermission;
import cn.zhengjunren.myblog.system.mapper.TbRolePermissionMapper;
import cn.zhengjunren.myblog.system.service.TbRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TbRolePermissionServiceImpl implements TbRolePermissionService{

    @Resource
    private TbRolePermissionMapper tbRolePermissionMapper;




    @Override
    public List<Long> selectPermissionIdsByRoleId(Long roleId) {
        return tbRolePermissionMapper.selectPermissionIdsByRoleId(roleId);
    }

    @Override
    public int update(List<Long> permissionIds, Long roleId) {
        List<Long> oldPermissionIds = selectPermissionIdsByRoleId(roleId);
        //删除权限
        HashSet<Long> oldPermission = new HashSet<>(oldPermissionIds);
        HashSet<Long> newPermission = new HashSet<>(permissionIds);
        int result = 0;
        if (permissionIds.size() < oldPermissionIds.size()){
            oldPermission.removeAll(newPermission);
            List<Long> list = new ArrayList<>(oldPermission);
            result = deleteBatch(list, roleId);
        }
        //增加权限
        else {
            newPermission.removeAll(oldPermission);
            List<TbRolePermission> tbRolePermissions = new ArrayList<>();
            if (newPermission.isEmpty()) {
                return 2;
            }
            for (Long aLong : newPermission) {
                tbRolePermissions.add(new TbRolePermission(roleId, aLong));
            }
            result = tbRolePermissionMapper.insertList(tbRolePermissions);
        }

        return result;
    }

    @Override
    public int deleteBatch(List<Long> permissionIds, Long roleId) {
        return tbRolePermissionMapper.deleteBatch(permissionIds, roleId);
    }


}
