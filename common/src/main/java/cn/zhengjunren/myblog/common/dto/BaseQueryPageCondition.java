package cn.zhengjunren.myblog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>ClassName: BaseQueryPageCondition</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/24 23:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseQueryPageCondition {

    /**
     * 页码
     */
    protected long page;
    /**
     * 每页多少数据
     */
    protected long limit;
}
