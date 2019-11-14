package cn.zhengjunren.myblog.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    private String status;
}
