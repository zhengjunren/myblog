package cn.zhengjunren.myblog.system.dto.condition;

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
 * @date 2019/12/25 0:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQueryCondition extends BaseQueryPageCondition {

    Timestamp start;
    Timestamp end;
    String type;
}
