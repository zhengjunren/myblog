package cn.zhengjunren.myblog.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: PasswordParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/30 19:51
 */
@Data
public class PasswordParams implements Serializable {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
