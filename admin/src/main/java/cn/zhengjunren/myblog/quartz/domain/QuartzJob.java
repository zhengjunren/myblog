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
@TableName(value = "quartz_job")
public class QuartzJob extends BaseDomain implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";

    /**
     * Spring Bean名称
     */
    @TableField(value = "bean_name")
    private String beanName;

    /**
     * cron 表达式
     */
    @TableField(value = "cron_expression")
    private String cronExpression;

    /**
     * 状态：1暂停、0启用
     */
    @TableField(value = "is_pause")
    private Boolean isPause;

    /**
     * 任务名称
     */
    @TableField(value = "job_name")
    private String jobName;

    /**
     * 方法名称
     */
    @TableField(value = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    private static final long serialVersionUID = 1L;

    public static final String COL_BEAN_NAME = "bean_name";

    public static final String COL_CRON_EXPRESSION = "cron_expression";

    public static final String COL_IS_PAUSE = "is_pause";

    public static final String COL_JOB_NAME = "job_name";

    public static final String COL_METHOD_NAME = "method_name";

    public static final String COL_PARAMS = "params";

    public static final String COL_REMARK = "remark";
}
