package cn.zhengjunren.myblog.quartz.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "quartz_log")
public class QuartzLog extends BaseDomain implements Serializable {

    @ExcelProperty("SpringBean名称")
    @TableField(value = "bean_name")
    private String beanName;

    @ExcelProperty("cron表达式")
    @TableField(value = "cron_expression")
    private String cronExpression;

    @ExcelProperty("异常详情")
    @TableField(value = "exception_detail")
    private String exceptionDetail;

    @ExcelProperty("是否成功")
    @TableField(value = "is_success")
    private Boolean isSuccess;

    @ExcelProperty("任务名称")
    @TableField(value = "job_name")
    private String jobName;

    @ExcelProperty("方法名称")
    @TableField(value = "method_name")
    private String methodName;

    @ExcelProperty("参数")
    @TableField(value = "params")
    private String params;

    @ExcelProperty("耗时")
    @TableField(value = "time")
    private Long time;


    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    public static final String COL_BAEN_NAME = "baen_name";

    @ExcelIgnore
    public static final String COL_CRON_EXPRESSION = "cron_expression";

    @ExcelIgnore
    public static final String COL_EXCEPTION_DETAIL = "exception_detail";

    @ExcelIgnore
    public static final String COL_IS_SUCCESS = "is_success";

    @ExcelIgnore
    public static final String COL_JOB_NAME = "job_name";

    @ExcelIgnore
    public static final String COL_METHOD_NAME = "method_name";

    @ExcelIgnore
    public static final String COL_PARAMS = "params";

    @ExcelIgnore
    public static final String COL_TIME = "time";
}
