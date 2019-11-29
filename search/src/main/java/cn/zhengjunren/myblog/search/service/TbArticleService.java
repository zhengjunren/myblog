package cn.zhengjunren.myblog.search.service;

import cn.zhengjunren.myblog.search.domain.TbArticle;

import java.util.List;

/**
 * <p>ClassName: TbArticleService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 23:32
 */
public interface TbArticleService{
    /**
     * 获取所有文章
     * @return {@link List<TbArticle>}
     */
    List<TbArticle> selectAll();
}
