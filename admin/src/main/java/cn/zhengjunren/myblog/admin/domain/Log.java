package cn.zhengjunren.myblog.admin.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZhengJunren
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "log")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Log extends BaseDomain implements Serializable {

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }

    @TableField(value = "description")
    private String description;

    @TableField(value = "exception_detail")
    private byte[] exceptionDetail;

    @TableField(value = "log_type")
    private String logType;

    @TableField(value = "method")
    private String method;

    @TableField(value = "params")
    private String params;

    @TableField(value = "request_ip")
    private String requestIp;

    @TableField(value = "time")
    private Long time;

    @TableField(value = "username")
    private String username;

    @TableField(value = "address")
    private String address;

    @TableField(value = "browser")
    private String browser;

    private static final long serialVersionUID = 1L;

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_EXCEPTION_DETAIL = "exception_detail";

    public static final String COL_LOG_TYPE = "log_type";

    public static final String COL_METHOD = "method";

    public static final String COL_PARAMS = "params";

    public static final String COL_REQUEST_IP = "request_ip";

    public static final String COL_TIME = "time";

    public static final String COL_USERNAME = "username";

    public static final String COL_ADDRESS = "address";

    public static final String COL_BROWSER = "browser";
}
