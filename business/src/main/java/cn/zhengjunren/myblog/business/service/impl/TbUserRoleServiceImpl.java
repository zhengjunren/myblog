package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.domain.TbUserRole;
import cn.zhengjunren.myblog.business.mapper.TbUserRoleMapper;
import cn.zhengjunren.myblog.business.service.TbUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbUserRoleServiceImpl implements TbUserRoleService{

    @Resource
    private TbUserRoleMapper tbUserRoleMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(TbUserRole tbUserRole, long oldRoleId) {
        Example example = new Example(TbUserRole.class);
        example.createCriteria()
                .andEqualTo("userId", tbUserRole.getUserId())
                .andEqualTo("roleId", oldRoleId);
        return tbUserRoleMapper.updateByExample(tbUserRole, example);
    }
}
