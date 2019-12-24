package cn.zhengjunren.myblog.quartz.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "quartz_log")
public class QuartzLog extends BaseDomain implements Serializable {
    @TableField(value = "bean_name")
    private String beanName;

    @TableField(value = "cron_expression")
    private String cronExpression;

    @TableField(value = "exception_detail")
    private String exceptionDetail;

    @TableField(value = "is_success")
    private Boolean isSuccess;

    @TableField(value = "job_name")
    private String jobName;

    @TableField(value = "method_name")
    private String methodName;

    @TableField(value = "params")
    private String params;

    @TableField(value = "time")
    private Long time;

    private static final long serialVersionUID = 1L;

    public static final String COL_BAEN_NAME = "baen_name";

    public static final String COL_CRON_EXPRESSION = "cron_expression";

    public static final String COL_EXCEPTION_DETAIL = "exception_detail";

    public static final String COL_IS_SUCCESS = "is_success";

    public static final String COL_JOB_NAME = "job_name";

    public static final String COL_METHOD_NAME = "method_name";

    public static final String COL_PARAMS = "params";

    public static final String COL_TIME = "time";
}
