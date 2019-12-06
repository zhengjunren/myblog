package cn.zhengjunren.myblog.system.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ZhengJunren
 */
@Data
@Table(name = "tb_role_permission")
public class TbRolePermission implements Serializable {

    private static final long serialVersionUID = -337143745184171816L;

    public TbRolePermission(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 角色 ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限 ID
     */
    @Column(name = "permission_id")
    private Long permissionId;
}
