package cn.zhengjunren.myblog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhengJunren
 */
@Data
@Table(name = "tb_log")
public class TbLog implements Serializable {
    private static final long serialVersionUID = -3843478305200544636L;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "description")
    private String description;

    @Column(name = "exception_detail")
    private String exceptionDetail;

    @Column(name = "log_type")
    private String logType;

    @Column(name = "`method`")
    private String method;

    @Column(name = "params")
    private String params;

    @Column(name = "request_ip")
    private String requestIp;

    @Column(name = "`time`")
    private Long time;

    @Column(name = "username")
    private String username;

    @Column(name = "address")
    private String address;

    @Column(name = "browser")
    private String browser;
}
