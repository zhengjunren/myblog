package cn.zhengjunren.myblog.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: PermissionParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/5 17:01
 */
@Data
public class PermissionParams implements Serializable {
    Long currentRoleId;
    List<Long> permissionIds;
}
