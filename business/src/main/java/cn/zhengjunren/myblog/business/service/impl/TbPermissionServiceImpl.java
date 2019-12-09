package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.mapper.TbPermissionMapper;
import cn.zhengjunren.myblog.business.service.TbPermissionService;
import cn.zhengjunren.myblog.commons.domain.TbPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhengJunren
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbPermissionServiceImpl implements TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;


    @Override
    public List<TbPermission> selectAll() {
        return tbPermissionMapper.selectAll();
    }
}
