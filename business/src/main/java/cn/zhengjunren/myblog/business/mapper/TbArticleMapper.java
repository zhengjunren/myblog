package cn.zhengjunren.myblog.business.mapper;

import cn.zhengjunren.myblog.business.domain.TbArticle;
import cn.zhengjunren.myblog.business.dto.TbArticleNoContent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

/**
 * <p>ClassName: TbArticleMapper</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 13:18
 */

public interface TbArticleMapper extends MyMapper<TbArticle> {

    /**
     * 获取文章列表，文章信息中不包括内容
     * @return {@link List<TbArticleNoContent>}
     */
    List<TbArticleNoContent> selectAllNoContent();

    /**
     * 根据用户名获取最新发布的文章
     * @param number 获取的数量
     * @param username 用户名
     * @return {@link List<TbArticleNoContent>}
     */
    List<TbArticleNoContent> selectLatestArticle(@Param("number") Integer number, @Param("username")String username);
}
