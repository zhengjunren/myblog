package cn.zhengjunren.myblog.search.controller;

import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.search.domain.TbArticle;
import cn.zhengjunren.myblog.search.dto.ArticleSearchParams;
import cn.zhengjunren.myblog.search.service.TbArticleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * <p>ClassName: ArticleSearchController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 23:32
 */
@RestController
@RequestMapping("search")
public class ArticleSearchController {


    private final ElasticsearchTemplate template;

    private final TbArticleService tbArticleService;

    public ArticleSearchController(ElasticsearchTemplate template, TbArticleService tbArticleService) {
        this.template = template;
        this.tbArticleService = tbArticleService;
    }

    @MyLog("搜索文章")
    @PostMapping("article")
    public ResponseResult<ListInfo<TbArticle>> searchAll(@RequestBody ArticleSearchParams articleSearchParams, Integer page, Integer limit) {
        ListInfo<TbArticle> listInfo = new ListInfo<>();
        List<TbArticle> tbArticles = tbArticleService.selectAll();
        List<IndexQuery> queries = new ArrayList<>();
        int counter = 0;
        for (TbArticle tbArticle : tbArticles) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(String.valueOf(tbArticle.getId()));
            indexQuery.setObject(tbArticle);
            indexQuery.setIndexName("myblogarticle");
            indexQuery.setType("TbArticle");
            queries.add(indexQuery);
            if (counter % 500 == 0) {
                template.bulkIndex(queries);
            }
            counter++;
        }
        if (queries.size() > 0) {
            template.bulkIndex(queries);
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        setQuery(nativeSearchQueryBuilder, articleSearchParams);
        SearchQuery searchQuery = nativeSearchQueryBuilder
                .withPageable(PageRequest.of(page - 1, limit))
                .build();
        List<TbArticle> tbArticleList = template.queryForList(searchQuery, TbArticle.class);
        long count = template.count(searchQuery);
        listInfo.setItems(tbArticleList);
        listInfo.setTotal(count);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"搜索成功", listInfo);
    }

    private void setQuery(NativeSearchQueryBuilder nativeSearchQueryBuilder, Object object){
        if (object != null) {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(object) != null && !"".equals(field.get(object))){
                        System.out.println(field.getName());
                        nativeSearchQueryBuilder
                                .withQuery(matchQuery(field.getName(), field.get(object)));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
