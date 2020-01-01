package cn.zhengjunren.myblog.system.dto.condition;

import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * <p>ClassName: QiniuQueryCondition</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/1 17:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QiniuQueryCondition extends BaseQueryPageCondition {
    Timestamp start;
    Timestamp end;
    String key;
}
