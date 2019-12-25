package cn.zhengjunren.myblog.quartz.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "quartz_job")
public class QuartzJob extends BaseDomain implements Serializable {

    @ExcelIgnore
    public static final String JOB_KEY = "JOB_KEY";

    /**
     * Spring Bean名称
     */
    @ExcelProperty("SpringBean名称")
    @TableField(value = "bean_name")
    private String beanName;

    /**
     * cron 表达式
     */
    @ExcelProperty("cron表达式")
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 状态：1暂停、0启用
     */
    @ExcelProperty("状态：1暂停、0启用")
    @TableField(value = "is_pause")
    private Boolean isPause;

    /**
     * 任务名称
     */
    @ExcelProperty("任务名称")
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 方法名称
     */
    @ExcelProperty("方法名称")
    @TableField(value = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @ExcelProperty("参数")
    @TableField(value = "params")
    private String params;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    @TableField(value = "remark")
    private String remark;

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    public static final String COL_BEAN_NAME = "bean_name";

    @ExcelIgnore
    public static final String COL_CRON_EXPRESSION = "cron_expression";

    @ExcelIgnore
    public static final String COL_IS_PAUSE = "is_pause";

    @ExcelIgnore
    public static final String COL_JOB_NAME = "job_name";

    @ExcelIgnore
    public static final String COL_METHOD_NAME = "method_name";

    @ExcelIgnore
    public static final String COL_PARAMS = "params";

    @ExcelIgnore
    public static final String COL_REMARK = "remark";
}
