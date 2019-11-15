package cn.zhengjunren.myblog.search.service;

import cn.zhengjunren.myblog.search.domain.TbUser;

import java.util.List;

/**
 * <p>ClassName: TbUserService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 15:04
 */
public interface TbUserService{
    List<TbUser> selectAll();
}
