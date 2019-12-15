package cn.zhengjunren.myblog.security.mapper;

import cn.zhengjunren.myblog.security.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据角色id列表查询权限
     * @param ids 角色id列表
     * @return 权限
     */
    List<Permission> selectByRoleIdList(@Param("ids") List<Long> ids);
}
