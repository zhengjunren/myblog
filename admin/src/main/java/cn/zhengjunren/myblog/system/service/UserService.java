package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * @author ZhengJunren
 */
public interface UserService extends IService<User>{

    /**
     * 根据用户名或邮箱或电话获取用户
     * @param usernameOrEmailOrPhone 用户名、邮件、电话
     * @return {@link User}
     */
    User selectOneByUsernameOrEmailOrPhone(String usernameOrEmailOrPhone);
}
