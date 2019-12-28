package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "visits")
public class Visits extends BaseDomain implements Serializable {
    @TableField(value = "date")
    private String date;

    @TableField(value = "ip_counts")
    private Long ipCounts;

    @TableField(value = "pv_counts")
    private Long pvCounts;

    @TableField(value = "week_day")
    private String weekDay;

    private static final long serialVersionUID = 1L;

    public static final String COL_DATE = "date";

    public static final String COL_IP_COUNTS = "ip_counts";

    public static final String COL_PV_COUNTS = "pv_counts";

    public static final String COL_WEEK_DAY = "week_day";
}
