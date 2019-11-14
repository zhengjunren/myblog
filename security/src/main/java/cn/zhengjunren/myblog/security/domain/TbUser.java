package cn.zhengjunren.myblog.security.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Table(name = "tb_user")
public class TbUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "url")
    private String url;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "register_time")
    private LocalDateTime registerTime;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "`status`")
    private String status;
}