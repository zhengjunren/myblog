package cn.zhengjunren.myblog.business.service;

import cn.zhengjunren.myblog.business.domain.TbArticle;
import cn.zhengjunren.myblog.business.dto.TbArticleNoContent;
import com.github.pagehelper.PageInfo;

/**
 * <p>ClassName: TbArticleService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 13:18
 */
public interface TbArticleService{

    /**
     * 分页获取文章
     * @param pageNum 页数
     * @param pageSize 笔数
     * @return {@link TbArticleNoContent}
     */
    PageInfo<TbArticleNoContent> page(Integer pageNum, Integer pageSize);

    /**
     * 根据 id 获取文章
     * @param id id
     * @return {@link TbArticle}
     */
    TbArticle getById(Integer id);

    /**
     * 发布文章
     * @param tbArticle {@link TbArticle}
     * @return 结果
     */
    int insert(TbArticle tbArticle);

    /**
     * 更新
     * @param tbArticle {@link TbArticle}
     * @return 结果
     */
    int update(TbArticle tbArticle);
}
