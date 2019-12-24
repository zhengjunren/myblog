package cn.zhengjunren.myblog.system.dto.params;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: PermissionParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 11:50
 */
@Data
public class PermissionParams implements Serializable {
    private static final long serialVersionUID = -2297861581553352631L;
    private long currentRoleId;
    List<Long> permissionIds;
}
