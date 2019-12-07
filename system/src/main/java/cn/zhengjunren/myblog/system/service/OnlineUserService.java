package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.OnlineUser;

import java.util.List;

/**
 * <p>ClassName: OnlineUserService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/7 9:48
 */
public interface OnlineUserService {

    /**
     * 获取当前在线用户
     * @return
     */
    List<OnlineUser> selectAll(String filter);
}
