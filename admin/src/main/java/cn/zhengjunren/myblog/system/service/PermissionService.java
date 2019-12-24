package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface PermissionService extends IService<Permission>{


    /**
     *根据角色 id 列表获取相应的权限
     * @param ids 角色 id 列表
     * @return {@link List <Permission>}
     */
    List<Permission> selectByRoleIdList(List<Long> ids);


    /**
     * 根据父 id 获取权限
     * @param parentId 父 id
     * @return 权限
     */
    List<Permission> selectByParentId(long parentId);

    /**
     * 权限树
     * @param permissions 权限
     * @return 结果
     */
    Object getPermissionTree(List<Permission> permissions);
}
