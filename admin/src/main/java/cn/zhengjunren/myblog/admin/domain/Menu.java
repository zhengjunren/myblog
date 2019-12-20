package cn.zhengjunren.myblog.admin.domain;

import cn.zhengjunren.myblog.admin.common.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhengJunren
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "menu")
public class Menu extends BaseDomain implements Serializable {
    /**
     * 是否外链
     */
    @TableField(value = "i_frame")
    private Boolean iFrame;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 组件
     */
    @TableField(value = "component")
    private String component;

    /**
     * 上级菜单ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Long sort;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 链接地址
     */
    @TableField(value = "path")
    private String path;

    @TableField(value = "cache")
    private Boolean cache;

    @TableField(value = "hidden")
    private Boolean hidden;

    @TableField(value = "component_name")
    private String componentName;

    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "permission")
    private String permission;

    @TableField(value = "type")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_I_FRAME = "i_frame";

    public static final String COL_NAME = "name";

    public static final String COL_COMPONENT = "component";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_SORT = "sort";

    public static final String COL_ICON = "icon";

    public static final String COL_PATH = "path";

    public static final String COL_CACHE = "cache";

    public static final String COL_HIDDEN = "hidden";

    public static final String COL_COMPONENT_NAME = "component_name";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_PERMISSION = "permission";

    public static final String COL_TYPE = "type";
}
