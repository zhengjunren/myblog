package cn.zhengjunren.myblog.system.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_email_config")
public class TbEmailConfig {
    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 发件人
     */
    @Column(name = "from_user")
    private String fromUser;

    /**
     * 邮件服务器SMTP地址
     */
    @Column(name = "`host`")
    private String host;

    /**
     * 密码
     */
    @Column(name = "pass")
    private String pass;

    /**
     * 端口
     */
    @Column(name = "port")
    private String port;

    /**
     * 发件者用户名
     */
    @Column(name = "`user`")
    private String user;
}
