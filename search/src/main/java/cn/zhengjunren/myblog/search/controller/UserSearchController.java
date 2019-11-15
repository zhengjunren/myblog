package cn.zhengjunren.myblog.search.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.search.domain.TbUser;
import cn.zhengjunren.myblog.search.dto.UserListInfo;
import cn.zhengjunren.myblog.search.dto.UserSearchParm;
import cn.zhengjunren.myblog.search.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * <p>ClassName: SearchController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 14:59
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("user")
public class UserSearchController {

    @Autowired
    ElasticsearchTemplate template;

    @Autowired
    TbUserService tbUserService;


    @PostMapping("search")
    public ResponseResult< UserListInfo > searchAll(@RequestBody UserSearchParm userSearchParm) {
        List<TbUser> tbUsers = tbUserService.selectAll();
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

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        setQuery(nativeSearchQueryBuilder, userSearchParm);
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();

        List<TbUser> tbUserList = template.queryForList(searchQuery, TbUser.class);
        long count = template.count(searchQuery);
        UserListInfo userListInfo = new UserListInfo();
        userListInfo.setItems(tbUserList);
        userListInfo.setTotal(count);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"搜索成功", userListInfo);
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
