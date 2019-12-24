package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
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
@TableName(value = "role")
public class Role extends BaseDomain implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = -3384570942342646435L;
    /**
     * 角色名
     */
    @ExcelProperty("角色名")
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @ExcelProperty("角色描述")
    @TableField(value = "description")
    private String description;

    /**
     * 更新时间
     */
    @ExcelProperty("更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time")
    private Date updateTime;

    @ExcelIgnore
    public static final String COL_NAME = "name";

    @ExcelIgnore
    public static final String COL_DESCRIPTION = "description";


    @ExcelIgnore
    public static final String COL_UPDATE_TIME = "update_time";
}
