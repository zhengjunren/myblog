package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.mapper.TbRoleMapper;
import cn.zhengjunren.myblog.system.service.TbRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;

    @Override
    public List<TbRole> selectAll() {
        return tbRoleMapper.selectAll();
    }

    @Override
    public PageInfo<TbRole> page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbRole> tbRoles = tbRoleMapper.selectAll();
        return new PageInfo<>(tbRoles);
    }

    @Override
    public int insert(TbRole tbRole) {
        tbRole.setCreated(new Date());
        tbRole.setUpdated(new Date());
        return tbRoleMapper.insert(tbRole);
    }

    @Override
    public int update(TbRole tbRole) {
        tbRole.setUpdated(new Date());
        return tbRoleMapper.updateByPrimaryKey(tbRole);
    }

}
