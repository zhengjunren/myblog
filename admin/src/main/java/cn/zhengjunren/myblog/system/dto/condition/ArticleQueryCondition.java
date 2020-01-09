package cn.zhengjunren.myblog.system.dto.condition;

import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * <p>ClassName: ArticleQueryCondition</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/9 10:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryCondition extends BaseQueryPageCondition {
    Timestamp start;
    Timestamp end;
    String username;
    Integer status;
    Long categoryId;
}
