package cn.zhengjunren.myblog.system.domain;

import javax.persistence.*;
import lombok.Data;

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
     * 收件人
     */
    @Column(name = "`to`")
    private String to;

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
    @Column(name = "`from`")
    private String from;
}