package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
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
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article")
public class Article extends BaseDomain implements Serializable {
    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 分类ID
     */
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 摘要
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 浏览数
     */
    @TableField(value = "view_count")
    private Integer viewCount;

    /**
     * 评论数
     */
    @TableField(value = "comment_count")
    private Integer commentCount;

    /**
     * 点赞数
     */
    @TableField(value = "like_count")
    private Integer likeCount;

    /**
     * 允许评论
     */
    @TableField(value = "is_comment")
    private Boolean isComment;

    /**
     * 状态： 1：发布、2：草稿、3：删除
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 编辑时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_CATEGORY_ID = "category_id";

    public static final String COL_TITLE = "title";

    public static final String COL_SUMMARY = "summary";

    public static final String COL_CONTENT = "content";

    public static final String COL_VIEW_COUNT = "view_count";

    public static final String COL_COMMENT_COUNT = "comment_count";

    public static final String COL_LIKE_COUNT = "like_count";

    public static final String COL_IS_COMMENT = "is_comment";

    public static final String COL_STATUS = "status";

    public static final String COL_ORDER = "order";

    public static final String COL_UPDATE_TIME = "update_time";
}
