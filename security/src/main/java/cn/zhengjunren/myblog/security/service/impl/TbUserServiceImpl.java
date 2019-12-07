package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.commons.domain.TbUser;
import cn.zhengjunren.myblog.security.mapper.TbUserMapper;
import cn.zhengjunren.myblog.security.service.TbUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
}
