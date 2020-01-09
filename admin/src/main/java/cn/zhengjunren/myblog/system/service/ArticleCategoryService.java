package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.ArticleCategory;
import cn.zhengjunren.myblog.system.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {


    /**
     * 获取文章分类树
     * @param articleCategories {@link List < ArticleCategory >}
     * @return 结果
     */
    Object getMenuTree(List<ArticleCategory> articleCategories);

    /**
     * 根据父 id 查询
     * @param parentId 父 id
     * @return {@link List< Menu >}
     */
    List<ArticleCategory> findByParentId(Long parentId);

    /**
     * 根据 id 获取子 id（包括子节点的子节点）
     * @param id id
     * @return /
     */
    List<Long> getChildrenIds(Long id);
}


