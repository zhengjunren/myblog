package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.system.domain.Article;
import cn.zhengjunren.myblog.system.dto.condition.ArticleQueryCondition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询
     * @param condition 查询条件
     * @return 通用结果
     */
    ListInfo page(ArticleQueryCondition condition, List<Long> ids);

    /**
     * 更新评论状态
     * @param id id
     */
    void updateComment(Long id);
}

