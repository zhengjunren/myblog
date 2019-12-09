package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.domain.TbRolePermission;
import cn.zhengjunren.myblog.system.mapper.TbRoleMapper;
import cn.zhengjunren.myblog.system.mapper.TbRolePermissionMapper;
import cn.zhengjunren.myblog.system.service.TbRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author ZhengJunren
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbRolePermissionServiceImpl implements TbRolePermissionService{

    @Resource
    private TbRolePermissionMapper tbRolePermissionMapper;

    @Resource
    private TbRoleMapper tbRoleMapper;


    @Override
    public List<Long> selectPermissionIdsByRoleEnName(String roleEnName) {
        return tbRolePermissionMapper.selectPermissionIdsByRoleEnName(roleEnName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(List<Long> permissionIds, String roleEnName) {
        List<Long> oldPermissionIds = selectPermissionIdsByRoleEnName(roleEnName);
        //删除权限
        HashSet<Long> oldPermission = new HashSet<>(oldPermissionIds);
        HashSet<Long> newPermission = new HashSet<>(permissionIds);
        int result = 0;
        Example example = new Example(TbRole.class);
        example.createCriteria().andEqualTo("enname", roleEnName);
        TbRole tbRole = tbRoleMapper.selectOneByExample(example);
        if (permissionIds.size() < oldPermissionIds.size()){
            oldPermission.removeAll(newPermission);
            List<Long> list = new ArrayList<>(oldPermission);
            result = deleteBatch(list, tbRole.getId());
        }
        //增加权限
        else {
            newPermission.removeAll(oldPermission);
            List<TbRolePermission> tbRolePermissions = new ArrayList<>();
            if (newPermission.isEmpty()) {
                return 2;
            }
            for (Long aLong : newPermission) {
                tbRolePermissions.add(new TbRolePermission(tbRole.getId(), aLong));
            }
            result = tbRolePermissionMapper.insertList(tbRolePermissions);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(List<Long> permissionIds, Long roleId) {
        return tbRolePermissionMapper.deleteBatch(permissionIds, roleId);
    }


}
