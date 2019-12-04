package cn.zhengjunren.myblog.search.service.impl;

import cn.zhengjunren.myblog.search.domain.RoleAndUrl;
import cn.zhengjunren.myblog.search.domain.TbPermission;
import cn.zhengjunren.myblog.search.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.search.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<RoleAndUrl> getUrlWithRole() {
        return tbPermissionMapper.getUrlWithRole();
    }

    @Override
    public List<TbPermission> selectAll() {
        return tbPermissionMapper.selectAll();
    }
}
