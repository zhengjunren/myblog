package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.ArticleCategory;
import cn.zhengjunren.myblog.system.dto.ArticleCategoryDTO;
import cn.zhengjunren.myblog.system.mapper.ArticleCategoryMapper;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import cn.zhengjunren.myblog.system.service.mapper.ArticleCategoryMapperStruct;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZhengJunren
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {


    private final ArticleCategoryMapperStruct articleCategoryMapperStruct;

    public ArticleCategoryServiceImpl(ArticleCategoryMapperStruct articleCategoryMapperStruct) {
        this.articleCategoryMapperStruct = articleCategoryMapperStruct;
    }

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

    @Override
    public List<ArticleCategoryDTO> getAll() {
        QueryWrapper<ArticleCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(ArticleCategory.COL_SORT);
        List<ArticleCategory> articleCategories = baseMapper.selectList(queryWrapper);
        return articleCategoryMapperStruct.toDto(articleCategories);
    }

    @Override
    public Map<String, Object> buildTree(List<ArticleCategoryDTO> articleCategoryDTOS) {
        List<ArticleCategoryDTO> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (ArticleCategoryDTO categoryDTO : articleCategoryDTOS) {
            if (categoryDTO.getParentId() == 0) {
                trees.add(categoryDTO);
            }
            for (ArticleCategoryDTO it : articleCategoryDTOS) {
                if (it.getParentId().equals(categoryDTO.getId())) {
                    if (categoryDTO.getChildren() == null) {
                        categoryDTO.setChildren(new ArrayList<>());
                    }
                    categoryDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = articleCategoryDTOS.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("content", trees);
        map.put("totalElements", articleCategoryDTOS.size());
        return map;
    }
}


