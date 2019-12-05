package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.TbRolePermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbRolePermissionMapper extends MyMapper<TbRolePermission> {
    /**
     * 根据角色 id 获取相应的权限 id
     * @param roleId 角色 id
     * @return 权限 id
     */
    List<Long> selectPermissionIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色 id 批量输出
     * @param permissionIds 权限 id
     * @param roleId 角色 id
     * @return 结果
     */
    int deleteBatch(@Param("permissionIds") List<Long> permissionIds, @Param("roleId") Long roleId);
}
