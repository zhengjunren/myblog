package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.domain.TbUser;
import cn.zhengjunren.myblog.business.mapper.TbUserMapper;
import cn.zhengjunren.myblog.business.service.TbUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
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
    public int insert(TbUser tbUser) {
        return tbUserMapper.insert(tbUser);
    }

    @Override
    public PageInfo<TbUser> page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbUser> userList = tbUserMapper.selectAll();
        return new PageInfo<>(userList);
    }

    @Override
    public int update(TbUser tbUser) {
        TbUser user = tbUserMapper.selectByPrimaryKey(tbUser);
        tbUser.setPassword(user.getPassword());
        return tbUserMapper.updateByPrimaryKey(tbUser);
    }

    @Override
    public int modifyProfile(TbUser oldTbUser, TbUser newTbUser) {
        oldTbUser.setEmail(newTbUser.getEmail());
        oldTbUser.setAvatar(newTbUser.getAvatar());
        oldTbUser.setNickname(newTbUser.getNickname());
        oldTbUser.setUrl(newTbUser.getUrl());
        return tbUserMapper.updateByPrimaryKey(oldTbUser);
    }
}
