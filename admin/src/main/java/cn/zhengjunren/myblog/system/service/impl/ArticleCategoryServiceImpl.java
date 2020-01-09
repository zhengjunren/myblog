package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.ArticleCategory;
import cn.zhengjunren.myblog.system.mapper.ArticleCategoryMapper;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengJunren
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {

    @Override
    public Object getMenuTree(List<ArticleCategory> articleCategories) {
        List<Map<String,Object>> list = new LinkedList<>();
        articleCategories.forEach(articleCategory -> {
            if (articleCategory!=null){
                List<ArticleCategory> categories = findByParentId(articleCategory.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",articleCategory.getId());
                map.put("label",articleCategory.getName());
                if(categories!=null && categories.size()!=0){
                    map.put("children",getMenuTree(categories));
                }
                list.add(map);
            }
        });
        return list;
    }

    @Override
    public List<ArticleCategory> findByParentId(Long parentId) {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ArticleCategory.COL_PARENT_ID, parentId);
        queryWrapper.orderByAsc(ArticleCategory.COL_SORT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Long> getChildrenIds(Long id) {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ArticleCategory.COL_PARENT_ID, id);
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        List<ArticleCategory> categories = baseMapper.selectList(queryWrapper);
        for (ArticleCategory category : categories) {
            if (category.getIsParent()){
                ids.addAll(getChildrenIds(category.getId()));
            }
            else {
                ids.add(category.getId());
            }
        }
        return ids;
    }
}


