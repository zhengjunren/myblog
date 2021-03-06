package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.commons.domain.TbPermission;
import cn.zhengjunren.myblog.system.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengJunren
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> selectAll() {
        return tbPermissionMapper.selectAll();
    }

    @Override
    public Object getPermissionTree(List<TbPermission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
            if (permission!=null){
                List<TbPermission> tbPermissions = selectByParentId(permission.getId());
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

    @Override
    public List<TbPermission> selectByParentId(long parentId) {
        Example example = new Example(TbPermission.class);
        example.createCriteria().andEqualTo("parentId", parentId);
        return tbPermissionMapper.selectByExample(example);
    }

    @Override
    public List<TbPermission> selectByRoleEnName(String roleEnName) {
        return tbPermissionMapper.selectByRoleEnName(roleEnName);
    }


}
