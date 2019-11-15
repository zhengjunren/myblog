package cn.zhengjunren.myblog.search.service.impl;

import cn.zhengjunren.myblog.search.domain.TbUser;
import cn.zhengjunren.myblog.search.mapper.TbUserMapper;
import cn.zhengjunren.myblog.search.service.TbUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>ClassName: TbUserServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 15:04
 */

@Service
public class TbUserServiceImpl implements TbUserService{

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public List<TbUser> selectAll() {
        return tbUserMapper.selectAll();
    }
}
