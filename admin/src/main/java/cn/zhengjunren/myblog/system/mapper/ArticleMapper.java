package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.Article;
import cn.zhengjunren.myblog.system.dto.ArticleDTO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhengJunren
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章信息，带有分类名、用户名
     * @param page /
     * @return /
     */
    IPage<ArticleDTO> selectWithCateAndUser(Page<ArticleDTO> page, @Param(Constants.WRAPPER) Wrapper<ArticleDTO> queryWrapper);
}
