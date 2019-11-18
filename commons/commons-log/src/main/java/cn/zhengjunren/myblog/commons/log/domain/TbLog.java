package cn.zhengjunren.myblog.commons.log.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_log")
public class TbLog {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

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
