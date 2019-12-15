package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     *
     * @param ids
     * @return
     */
    List<Permission> selectByRoleIdList(@Param("ids") List<Long> ids);
}
