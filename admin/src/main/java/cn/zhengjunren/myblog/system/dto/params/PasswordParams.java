package cn.zhengjunren.myblog.system.dto.params;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: PasswordParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/4 0:22
 */
@Data
public class PasswordParams implements Serializable {

    private static final long serialVersionUID = 8198385209791392977L;

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;
}
