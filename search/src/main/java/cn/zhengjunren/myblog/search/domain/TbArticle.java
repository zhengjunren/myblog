package cn.zhengjunren.myblog.search.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>ClassName: TbArticle</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 23:32
 */
@Data
@Document(indexName = "myblogarticle", type = "TbArticle")
@Table(name = "tb_article")
public class TbArticle {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "content")
    private String content;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "is_comment")
    private Integer isComment;

    @Column(name = "`status`")
    private Integer status;

    @Column(name = "`order`")
    private Integer order;

}
