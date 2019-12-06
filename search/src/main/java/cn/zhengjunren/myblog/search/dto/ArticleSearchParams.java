package cn.zhengjunren.myblog.search.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: ArticleSearchParams</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 23:44
 */
@Data
public class ArticleSearchParams implements Serializable {
    private static final long serialVersionUID = 7885117497097781311L;
    private String content;
    private String title;
}
