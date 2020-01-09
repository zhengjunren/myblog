package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ZhengJunren
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article_category")
public class ArticleCategory extends BaseDomain implements Serializable {
    public static final String COL_STATUS = "status";
    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 分类名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 该类目是否为父类目，1为true，0为false
     */
    @TableField(value = "is_parent")
    private Boolean isParent;

    /**
     * 创建时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_NAME = "name";

    public static final String COL_SORT = "sort";

    public static final String COL_IS_PARENT = "is_parent";

    public static final String COL_UPDATE_TIME = "update_time";
}
