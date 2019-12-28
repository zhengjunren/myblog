package cn.zhengjunren.myblog.system.dto.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>ClassName: LoginRequest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 22:16
 */
@Data
public class LoginParams {

    /**
     * 用户名或邮箱或手机号
     */
    @NotBlank(message = "用户名不能为空")
    private String usernameOrEmailOrPhone;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;


    private String uuid;

    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 记住我
     */
    private Boolean rememberMe = false;

    @Override
    public String toString() {
        return "LoginParams{" +
                "usernameOrEmailOrPhone='" + usernameOrEmailOrPhone + '\'' +
                ", password='" + "******" + '\'' +
                '}';
    }
}
