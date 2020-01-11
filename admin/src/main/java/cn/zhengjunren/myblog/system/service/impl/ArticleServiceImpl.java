package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.system.domain.Article;
import cn.zhengjunren.myblog.system.dto.ArticleDTO;
import cn.zhengjunren.myblog.system.dto.condition.ArticleQueryCondition;
import cn.zhengjunren.myblog.system.mapper.ArticleMapper;
import cn.zhengjunren.myblog.system.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: ArticleServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/8 20:12
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {



    @Override
    public ListInfo page(ArticleQueryCondition condition, List<Long> ids) {
        Page<ArticleDTO> page = new Page<>(condition.getPage(), condition.getLimit());
        QueryWrapper<ArticleDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(condition.getUsername()), ArticleDTO.COL_USERNAME, condition.getUsername());
        queryWrapper.eq(condition.getStatus() != null, "article.status", condition.getStatus());
        queryWrapper.in(ids != null, ArticleDTO.COL_CATEGORY_ID, ids);
        boolean flag = condition.getStart() != null && condition.getEnd() != null;
        queryWrapper.between(flag, "article.create_time", condition.getStart(), condition.getEnd());
        IPage<ArticleDTO> articleDTOIPage = baseMapper.selectWithCateAndUser(page, queryWrapper);
        return new ListInfo(articleDTOIPage.getRecords(), articleDTOIPage.getTotal());
    }

    @Override
    public void updateComment(Long id) {
        Article article = baseMapper.selectById(id);
        article.setIsComment(!article.getIsComment());
        article.setUpdateTime(new Date());
        baseMapper.updateById(article);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Article article = baseMapper.selectById(id);
        article.setStatus(status);
        article.setUpdateTime(new Date());
        baseMapper.updateById(article);
    }
}

