package cn.zhengjunren.myblog.search.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.search.domain.TbUser;
import cn.zhengjunren.myblog.search.service.TbUserService;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * <p>ClassName: SearchController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 14:59
 */
@RestController
@RequestMapping("user")
public class UserSearchController {

    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    TbUserService tbUserService;


    @GetMapping("all")
    public ResponseResult< List<TbUser> > searchAll() {
        List<TbUser> tbUsers = tbUserService.selectAll();
        IndexQueryBuilder builder = new IndexQueryBuilder();
        List<IndexQuery> queries = new ArrayList<>();
        int counter = 0;
        for (TbUser tbUser : tbUsers) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(String.valueOf(tbUser.getId()));
            indexQuery.setObject(tbUser);
            indexQuery.setIndexName("myblog");
            indexQuery.setType("TbUser");
            queries.add(indexQuery);
            if (counter % 500 == 0) {
                template.bulkIndex(queries);
            }
            counter++;
        }
        if (queries.size() > 0) {
            template.bulkIndex(queries);
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery("俊"))
                .withHighlightFields(new HighlightBuilder.Field("username"))
                .build();
        List<TbUser> tbUserList = template.queryForList(searchQuery, TbUser.class);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"搜素成功", tbUserList);
    }
}
