package cn.zhengjunren.myblog.search.test;

import cn.zhengjunren.myblog.commons.utils.MapperUtils;
import cn.zhengjunren.myblog.search.SearchApplication;
import cn.zhengjunren.myblog.search.test.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * <p>ClassName: SearchTest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 14:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchTest {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testQuery3(){
        List<Book> books = elasticsearchTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(matchQuery("name", "红")).build(), Book.class);
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    @Test
    public void test() throws Exception {
        Map<String, Object> map = MapperUtils.json2map("{\n" +
                "    \"settings\" : {\n" +
                "        \"analysis\" : {\n" +
                "            \"analyzer\" : {\n" +
                "                \"pinyin_analyzer\" : {\n" +
                "                    \"tokenizer\" : \"my_pinyin\"\n" +
                "                    }\n" +
                "            },\n" +
                "            \"tokenizer\" : {\n" +
                "                \"my_pinyin\" : {\n" +
                "                    \"type\" : \"pinyin\",\n" +
                "                    \"keep_separate_first_letter\" : false,\n" +
                "                    \"keep_full_pinyin\" : true,\n" +
                "                    \"keep_original\" : true,\n" +
                "                    \"limit_first_letter_length\" : 16,\n" +
                "                    \"lowercase\" : true,\n" +
                "                    \"remove_duplicated_term\" : true\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}");
        System.out.println(MapperUtils.mapToJson(map));
//        System.out.println(map.toString());
    }
}
