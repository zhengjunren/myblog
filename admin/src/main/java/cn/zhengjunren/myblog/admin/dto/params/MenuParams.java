package cn.zhengjunren.myblog.admin.dto.params;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: MenuParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 12:43
 */
@Data
public class MenuParams implements Serializable {
    private static final long serialVersionUID = -6165491761746474604L;
    private long currentRoleId;
    List<Long> menuIds;
}
