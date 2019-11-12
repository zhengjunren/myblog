package cn.zhengjunren.myblog.security.dto;

import lombok.Data;

/**
 * <p>ClassName: LoginParam</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 11:04
 */
@Data
public class LoginParam {

    private String username;
    private String password;
}
