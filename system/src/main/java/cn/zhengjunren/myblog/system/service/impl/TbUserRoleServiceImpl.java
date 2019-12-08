package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbUserRole;
import cn.zhengjunren.myblog.system.mapper.TbUserRoleMapper;
import cn.zhengjunren.myblog.system.service.TbUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>ClassName: TbUserRoleServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/08 22:33
 */
@Service
public class TbUserRoleServiceImpl implements TbUserRoleService{

    @Resource
    private TbUserRoleMapper tbUserRoleMapper;

    @Override
    public int update(TbUserRole tbUserRole) {
        return tbUserRoleMapper.updateByPrimaryKey(tbUserRole);
    }

    @Override
    public int insert(TbUserRole tbUserRole) {
        return tbUserRoleMapper.insert(tbUserRole);
    }
}
