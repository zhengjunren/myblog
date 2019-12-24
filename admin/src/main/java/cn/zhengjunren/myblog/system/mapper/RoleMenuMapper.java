package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据角色查询菜单
     * @param roleId 角色id
     * @return 结果
     */
    List<RoleMenu> selectByRoleId(@Param("roleId") long roleId);

    /**
     * 批量删除
     * @param menuIds 菜单
     * @param roleId 角色id
     * @return
     */
    int deleteBatch(@Param("menuIds") List<Long> menuIds, @Param("roleId") long roleId);
}
