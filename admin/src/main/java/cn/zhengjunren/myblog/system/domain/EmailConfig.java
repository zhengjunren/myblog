package cn.zhengjunren.myblog.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZhengJunren
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "email_config")
public class EmailConfig implements Serializable {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发件人
     */
    @TableField(value = "from_user")
    private String fromUser;

    /**
     * 邮件服务器SMTP地址
     */
    @TableField(value = "host")
    private String host;

    /**
     * 密码
     */
    @TableField(value = "pass")
    private String pass;

    /**
     * 端口
     */
    @TableField(value = "port")
    private String port;

    /**
     * 发件者用户名
     */
    @TableField(value = "user")
    private String user;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_FROM_USER = "from_user";

    public static final String COL_HOST = "host";

    public static final String COL_PASS = "pass";

    public static final String COL_PORT = "port";

    public static final String COL_USER = "user";
}
