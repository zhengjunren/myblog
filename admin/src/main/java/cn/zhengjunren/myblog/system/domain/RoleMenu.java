package cn.zhengjunren.myblog.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "role_menu")
public class RoleMenu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.INPUT)
    private Long menuId;

    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_ROLE_ID = "role_id";
}
