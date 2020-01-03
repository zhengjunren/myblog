package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhengJunren
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名或邮箱或电话获取用户
     * @param usernameOrEmailOrPhone 用户名、邮件、电话
     * @return {@link User}
     */
    User selectOneByUsernameOrEmailOrPhone(@Param("usernameOrEmailOrPhone") String usernameOrEmailOrPhone);

    /**
     * 根据用户名更新头像
     * @param username 用户名
     * @param avatar 头像
     */
    void updateAvatarByUsername(@Param("username") String username, @Param("avatar") String avatar);

    /**
     * 根据用户名更新密码
     * @param username 用户名
     * @param password 密码
     */
    void updatePassword(@Param("username") String username, @Param("password") String password);
}
