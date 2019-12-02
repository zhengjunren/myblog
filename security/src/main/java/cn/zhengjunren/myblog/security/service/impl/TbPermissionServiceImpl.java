package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.security.domain.TbPermission;
import cn.zhengjunren.myblog.security.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.security.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbPermissionServiceImpl implements TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> selectByRole(String roleName) {
        return tbPermissionMapper.selectByRole(roleName);
    }
}
