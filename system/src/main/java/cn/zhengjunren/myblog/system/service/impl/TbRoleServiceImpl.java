package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbRole;
import cn.zhengjunren.myblog.system.mapper.TbRoleMapper;
import cn.zhengjunren.myblog.system.service.TbRoleService;
import cn.zhengjunren.myblog.system.service.TbUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;

    private final TbUserRoleService tbUserRoleService;

    public TbRoleServiceImpl(TbUserRoleService tbUserRoleService) {
        this.tbUserRoleService = tbUserRoleService;
    }

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
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbRole tbRole) {
        Example example = new Example(TbRole.class);
        example.createCriteria().andEqualTo("enname", tbRole.getEnname());
        TbRole tbRole1 = tbRoleMapper.selectOneByExample(example);
        if (tbRole1 == null) {
            tbRole.setCreated(new Date());
            tbRole.setUpdated(new Date());
            return tbRoleMapper.insert(tbRole);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TbRole tbRole) {
        tbRole.setUpdated(new Date());
        return tbRoleMapper.updateByPrimaryKey(tbRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(TbRole tbRole) {
        if (tbRole.getId() == null) {
            Example example = new Example(TbRole.class);
            example.createCriteria().andEqualTo("enname", tbRole.getEnname());
            tbRole = tbRoleMapper.selectOneByExample(example);
        }
        boolean result = tbUserRoleService.isExisted(tbRole.getId());
        if (result) {
            return 0;
        }
        return tbRoleMapper.delete(tbRole);
    }

}
