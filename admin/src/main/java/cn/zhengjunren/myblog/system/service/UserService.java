package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.User;
import cn.zhengjunren.myblog.system.dto.params.PasswordParams;
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

    /**
     * 根据用户名更新头像
     * @param username 用户名
     * @param avatar 头像
     */
    void updateAvatarByUsername(String username, String avatar);

    /**
     * 根据用户名更新密码
     * @param username 用户名
     * @param passwordParams 密码
     */
    void updatePassword(String username, PasswordParams passwordParams);

    /**
     * 更新
     * @param user {@link User}
     * @param username 用户名
     */
    void update(User user, String username);
}
