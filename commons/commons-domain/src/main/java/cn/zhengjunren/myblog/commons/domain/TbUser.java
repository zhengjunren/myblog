package cn.zhengjunren.myblog.commons.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * <p>ClassName: TbUser</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/7 10:18
 */
@Data
@Table(name = "tb_user")
public class TbUser implements Serializable {
    @ExcelIgnore
    private static final long serialVersionUID = -6211438561568769708L;
    @Id
    @Column(name = "id")
    @ExcelIgnore
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @ExcelProperty("用户名")
    @Column(name = "username")
    private String username;

    @ExcelIgnore
    @Column(name = "`password`")
    private String password;

    @ExcelProperty("昵称")
    @Column(name = "nickname")
    private String nickname;

    @ExcelProperty("邮箱")
    @Column(name = "email")
    private String email;

    @ExcelProperty("用户首页")
    @Column(name = "url")
    private String url;

    @ExcelProperty("头像地址")
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @ExcelProperty("注册时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "register_time")
    private Date registerTime;

    @ExcelProperty("上次登录时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @ExcelProperty("状态")
    @Column(name = "`status`")
    private String status;
}
