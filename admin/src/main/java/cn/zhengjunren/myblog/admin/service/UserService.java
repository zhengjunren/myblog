package cn.zhengjunren.myblog.admin.service;

import cn.zhengjunren.myblog.admin.domain.User;
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
