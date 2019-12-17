package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     *根据角色 id 获取相应的权限
     * @param ids 角色 id
     * @return {@link List<Permission>}
     */
    List<Permission> selectByRoleIdList(@Param("ids") List<Long> ids);
}
