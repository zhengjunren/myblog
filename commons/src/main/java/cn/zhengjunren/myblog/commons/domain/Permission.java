package cn.zhengjunren.myblog.commons.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 2945958934343719902L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

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



    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_URL = "url";

    public static final String COL_TYPE = "type";

    public static final String COL_PERMISSION = "permission";

    public static final String COL_METHOD = "method";

    public static final String COL_SORT = "sort";

    public static final String COL_PARENT_ID = "parent_id";
}
