package cn.zhengjunren.myblog.admin.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>ClassName: BaseDomain</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 12:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseDomain {
    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;
}
