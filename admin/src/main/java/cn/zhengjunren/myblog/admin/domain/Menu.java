package cn.zhengjunren.myblog.admin.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
@TableName(value = "menu")
public class Menu extends BaseDomain implements Serializable {
    /**
     * 是否外链
     */
    @ExcelProperty("是否外链")
    @TableField(value = "i_frame")
    private Boolean iFrame;

    /**
     * 菜单名称
     */
    @ExcelProperty("菜单名称")
    @TableField(value = "name")
    private String name;

    /**
     * 组件
     */
    @ExcelProperty("组件")
    @TableField(value = "component")
    private String component;

    /**
     * 上级菜单ID
     */
    @ExcelProperty("上级菜单ID")
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @ExcelProperty("排序")
    @TableField(value = "sort")
    private Long sort;

    /**
     * 图标
     */
    @ExcelProperty("图标")
    @TableField(value = "icon")
    private String icon;

    /**
     * 链接地址
     */
    @ExcelProperty("链接地址")
    @TableField(value = "path")
    private String path;

    @ExcelProperty("是否缓存")
    @TableField(value = "cache")
    private Boolean cache;

    @ExcelProperty("是否隐藏")
    @TableField(value = "hidden")
    private Boolean hidden;

    @ExcelProperty("组件名")
    @TableField(value = "component_name")
    private String componentName;

    @ExcelProperty("权限标识")
    @TableField(value = "permission")
    private String permission;

    @ExcelProperty("菜单类型")
    @TableField(value = "type")
    private Integer type;

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    public static final String COL_ID = "id";

    @ExcelIgnore
    public static final String COL_I_FRAME = "i_frame";

    @ExcelIgnore
    public static final String COL_NAME = "name";

    @ExcelIgnore
    public static final String COL_COMPONENT = "component";

    @ExcelIgnore
    public static final String COL_PARENT_ID = "parent_id";

    @ExcelIgnore
    public static final String COL_SORT = "sort";

    @ExcelIgnore
    public static final String COL_ICON = "icon";

    @ExcelIgnore
    public static final String COL_PATH = "path";

    @ExcelIgnore
    public static final String COL_CACHE = "cache";

    @ExcelIgnore
    public static final String COL_HIDDEN = "hidden";

    @ExcelIgnore
    public static final String COL_COMPONENT_NAME = "component_name";

    @ExcelIgnore
    public static final String COL_CREATE_TIME = "create_time";

    @ExcelIgnore
    public static final String COL_PERMISSION = "permission";

    @ExcelIgnore
    public static final String COL_TYPE = "type";
}
