package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.security.domain.TbRole;
import cn.zhengjunren.myblog.security.mapper.TbRoleMapper;
import cn.zhengjunren.myblog.security.service.TbRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;

    @Override
    public List<TbRole> getRoleByUsername(String username) {
        return tbRoleMapper.getRoleByUsername(username);
    }

}
