package cn.zhengjunren.myblog.system.dto.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;

    @NotBlank(message = "验证密码不能为空")
    private String confirmPassword;

    @Override
    public String toString() {
        return "PasswordParams{" +
                "oldPassword='" + "******" + '\'' +
                ", newPassword='" + "******" + '\'' +
                ", confirmPassword='" + "******" + '\'' +
                '}';
    }
}
