package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.domain.RoleAndUrl;
import cn.zhengjunren.myblog.business.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.business.service.TbPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhengJunren
 */
@Service
public class TbPermissionServiceImpl implements TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<RoleAndUrl> getUrlWithRole() {
        return tbPermissionMapper.getUrlWithRole();
    }
}
