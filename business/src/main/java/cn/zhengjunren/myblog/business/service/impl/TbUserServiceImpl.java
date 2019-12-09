package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.dto.TbUserWithRole;
import cn.zhengjunren.myblog.business.mapper.TbUserMapper;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.domain.TbUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>ClassName: TbUserServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbUserServiceImpl implements TbUserService{

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser getByUsername(String username) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username", username);
        return tbUserMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insert(TbUser tbUser) {
        return tbUserMapper.insert(tbUser);
    }

    @Override
    public PageInfo<TbUserWithRole> page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbUserWithRole> userList = tbUserMapper.selectAllWithRole();
        return new PageInfo<>(userList);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(TbUser tbUser) {
        TbUser user = tbUserMapper.selectByPrimaryKey(tbUser);
        tbUser.setPassword(user.getPassword());
        return tbUserMapper.updateByPrimaryKey(tbUser);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int modifyProfile(TbUser oldTbUser, TbUser newTbUser) {
        oldTbUser.setEmail(newTbUser.getEmail());
        oldTbUser.setAvatar(newTbUser.getAvatar());
        oldTbUser.setNickname(newTbUser.getNickname());
        oldTbUser.setUrl(newTbUser.getUrl());
        return tbUserMapper.updateByPrimaryKey(oldTbUser);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int modifyAvatar(String username, String path) {
        TbUser tbUser = getByUsername(username);
        tbUser.setAvatar(path);
        return tbUserMapper.updateByPrimaryKey(tbUser);
    }

    @Override
    public List<TbUser> selectAll() {
        return tbUserMapper.selectAll();
    }
}
