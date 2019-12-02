package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.RoleAndUrl;
import cn.zhengjunren.myblog.system.domain.TbPermission;
import cn.zhengjunren.myblog.system.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.system.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhengJunren
 */
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
