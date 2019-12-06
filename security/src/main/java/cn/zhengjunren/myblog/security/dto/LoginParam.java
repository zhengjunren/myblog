package cn.zhengjunren.myblog.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: LoginParam</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 11:04
 */
@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = -6205713002154519158L;
    private String username;
    private String password;
}
