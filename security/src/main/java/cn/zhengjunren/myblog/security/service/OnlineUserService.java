package cn.zhengjunren.myblog.security.service;

import cn.zhengjunren.myblog.commons.domain.TbUser;

import javax.servlet.http.HttpServletRequest;

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
     * 将在线用户的信息保存至 redis
     * @param tbUser 用户信息
     * @param request 请求信息
     * @param token 令牌
     */
    void save(TbUser tbUser, String token,HttpServletRequest request);

    /**
     * 删除在线用户
     * @param token 令牌
     */
    void delete(String token);
}
