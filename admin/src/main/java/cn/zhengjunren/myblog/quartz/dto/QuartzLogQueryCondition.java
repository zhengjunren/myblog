package cn.zhengjunren.myblog.quartz.dto;

import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * <p>ClassName: LogQueryCondition</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/25 16:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuartzLogQueryCondition extends BaseQueryPageCondition {
    Timestamp start;
    Timestamp end;
    Boolean isSuccess;
}
