package cn.zhengjunren.myblog.admin.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZhengJunren
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "permission")
public class Permission extends BaseDomain implements Serializable {

    /**
     * 权限名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 权限类型，页面-1，按钮-2
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 权限表达式
     */
    @TableField(value = "permission")
    private String permission;

    /**
     * 后端接口访问方式
     */
    @TableField(value = "method")
    private String method;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 父级id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_URL = "url";

    public static final String COL_TYPE = "type";

    public static final String COL_PERMISSION = "permission";

    public static final String COL_METHOD = "method";

    public static final String COL_SORT = "sort";

    public static final String COL_PARENT_ID = "parent_id";
}
