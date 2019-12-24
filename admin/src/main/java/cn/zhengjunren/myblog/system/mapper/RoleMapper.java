package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户 id 获取角色
     * @param userId 用户 id
     * @return 相应角色
     */
    List<Role> selectByUserId(@Param("userId") Long userId);

}
