package cn.zhengjunren.myblog.search.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

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
 * @date 2019/11/15 15:04
 */
@Document(indexName = "myblog", type = "TbUser")
@Data
@Table(name = "tb_user")
public class TbUser implements Serializable {
    private static final long serialVersionUID = -3157171635276115593L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Field(searchAnalyzer = "standard")
    @Column(name = "username")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Field(searchAnalyzer = "standard")
    @Column(name = "email")
    private String email;

    @Column(name = "url")
    private String url;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "register_time")
    private Date registerTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "`status`")
    private String status;
}
