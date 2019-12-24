package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.Role;
import cn.zhengjunren.myblog.common.dto.ListInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RoleService extends IService<Role>{

    /**
     * 更新角色信息
     * @param role 需要更新的角色信息
     * @return 影响结果
     */
    int updateRole(Role role);

    /**
     * 根据名称获取角色
     * @param name 名称
     * @return {@link Role}
     */
    Role selectByName(String name);

    /**
     *  分页查询
     * @param current 页码
     * @param size 每页显示条数
     * @return {@link ListInfo}
     */
    ListInfo page(long current, long size);

    /**
     * 通过用户 id 获取角色
     * @param userId 用户 id
     * @return 相应角色
     */
    List<Role> selectByUserId(Long userId);
}
