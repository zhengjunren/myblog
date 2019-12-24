package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 根据角色查询权限
     * @param roleId 角色id
     * @return 结果
     */
    List<RolePermission> selectByRoleId(@Param("roleId") long roleId);

    /**
     * 批量删除
     * @param permissionIds 权限
     * @param roleId 角色id
     * @return
     */
    int deleteBatch(@Param("permissionIds") List<Long> permissionIds, @Param("roleId") long roleId);
}
