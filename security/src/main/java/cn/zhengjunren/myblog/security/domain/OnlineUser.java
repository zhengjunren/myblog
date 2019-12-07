package cn.zhengjunren.myblog.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: OnlineUser</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/7 9:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser implements Serializable {
    private static final long serialVersionUID = -1077502535189514386L;
    private String username;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;
}
