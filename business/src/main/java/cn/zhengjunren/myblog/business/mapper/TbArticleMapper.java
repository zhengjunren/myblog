package cn.zhengjunren.myblog.business.mapper;

import cn.zhengjunren.myblog.business.domain.TbArticle;
import cn.zhengjunren.myblog.business.dto.TbArticleNoContent;
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

    List<TbArticleNoContent> selectAllNoContent();


}
