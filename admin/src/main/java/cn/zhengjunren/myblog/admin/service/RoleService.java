package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.Role;
import cn.zhengjunren.myblog.admin.dto.ListInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface RoleService extends IService<Role>{

    /**
     * 根据名称获取角色
     * @param name 名称
     * @return {@link Role}
     */
    Role selectByName(String name);

    ListInfo page(long current, long size);

    /**
     * 通过用户 id 获取角色
     * @param userId 用户 id
     * @return 相应角色
     */
    List<Role> selectByUserId(Long userId);
}
