package cn.zhengjunren.myblog.commons.log.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_log")
public class TbLog {
    @Id
    @ExcelIgnore
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ExcelProperty("操作时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @ExcelProperty("描述")
    @Column(name = "description")
    private String description;

    @ExcelProperty("异常")
    @Column(name = "exception_detail")
    private String exceptionDetail;

    @ExcelProperty("日志类型")
    @Column(name = "log_type")
    private String logType;

    @ExcelProperty("请求方法")
    @Column(name = "`method`")
    private String method;

    @ExcelProperty("请求参数")
    @Column(name = "params")
    private String params;

    @ExcelProperty("IP")
    @Column(name = "request_ip")
    private String requestIp;

    @ExcelProperty("耗时")
    @Column(name = "`time`")
    private Long time;

    @ExcelProperty("操作者")
    @Column(name = "username")
    private String username;

    @ExcelProperty("地点")
    @Column(name = "address")
    private String address;

    @ExcelProperty("浏览器")
    @Column(name = "browser")
    private String browser;

    public TbLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
