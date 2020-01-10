package cn.zhengjunren.myblog.system.dto;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: ArticleCategoryDTO</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/10 14:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCategoryDTO extends BaseDomain implements Serializable {
    private static final long serialVersionUID = -1129632137463564657L;


    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    private Integer sort;

    /**
     * 该类目是否为父类目，1为true，0为false
     */
    private Boolean isParent;

    /**
     * 更新时间
     */
    private Date updateTime;

    private List<ArticleCategoryDTO> children;
}
