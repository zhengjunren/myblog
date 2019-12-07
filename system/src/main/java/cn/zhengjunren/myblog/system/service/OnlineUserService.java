package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param filter 过滤器
     * @return {@link List<OnlineUser>}
     */
    List<OnlineUser> selectAll(String filter);

    /**
     *分页
     * @param filter 过滤器
     * @param pageNum 页数
     * @param pageSize 笔数
     * @return {@link List<OnlineUser>}
     */
//    List<OnlineUser> page(String filter, Integer pageNum, Integer pageSize);

    Page<OnlineUser> page(String filter, Pageable pageable);

    /**
     * 踢出用户
     * @param val 加密后的 token
     * @throws Exception 网络请求错误
     */
    void kickOut(String val) throws Exception;
}
