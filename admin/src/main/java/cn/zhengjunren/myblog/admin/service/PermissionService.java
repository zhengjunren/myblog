package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Permission;
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

}
