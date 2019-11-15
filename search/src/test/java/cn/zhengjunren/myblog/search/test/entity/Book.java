package cn.zhengjunren.myblog.search.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * <p>ClassName: Book</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "zheng", type = "book")
public class Book {
    private Integer id;
    private String name;
    private String author;

}
