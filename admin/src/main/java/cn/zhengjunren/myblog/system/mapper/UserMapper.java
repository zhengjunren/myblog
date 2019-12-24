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
}
