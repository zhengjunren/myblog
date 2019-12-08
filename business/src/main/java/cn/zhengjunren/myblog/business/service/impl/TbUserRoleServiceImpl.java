package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.domain.TbUserRole;
import cn.zhengjunren.myblog.business.mapper.TbUserRoleMapper;
import cn.zhengjunren.myblog.business.service.TbUserRoleService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
@Service
public class TbUserRoleServiceImpl implements TbUserRoleService{

    @Resource
    private TbUserRoleMapper tbUserRoleMapper;

    @Override
    public int update(TbUserRole tbUserRole) {
        Example example = new Example(TbUserRole.class);
        example.createCriteria()
                .andEqualTo("userId", tbUserRole.getUserId())
                .andEqualTo("roleId", tbUserRole.getRoleId());
        return tbUserRoleMapper.updateByExample(tbUserRole, example);
    }
}
