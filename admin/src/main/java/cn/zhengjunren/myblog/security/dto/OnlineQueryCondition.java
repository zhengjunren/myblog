package cn.zhengjunren.myblog.security.dto;

import cn.zhengjunren.myblog.common.dto.BaseQueryPageCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>ClassName: OnlineQueryCondition</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 15:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineQueryCondition extends BaseQueryPageCondition {
    String filter;
}
