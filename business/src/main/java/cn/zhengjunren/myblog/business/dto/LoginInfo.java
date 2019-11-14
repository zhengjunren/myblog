package cn.zhengjunren.myblog.business.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * <p>ClassName: LoginInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 13:18
 */
@Data
public class LoginInfo {

    private Integer id;

    private String name;

    private String nickname;

    private String email;

    private String url;

    private String avatar;

    private String lastLoginIp;

    private LocalDateTime registerTime;

    private LocalDateTime lastLoginTime;

    @Column(name = "`status`")
    private String status;
}
