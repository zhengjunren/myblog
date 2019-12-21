package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RoleMenuService extends IService<RoleMenu>{

    /**
     * 更新角色菜单
     * @param menuIds 需要更新的菜单
     * @param roleId 角色id
     * @return 影响条数
     */
    int updateMenu(List<Long> menuIds, long roleId);
}
