package cn.zhengjunren.myblog.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>ClassName: ListInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 13:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListInfo<T> {
    List<T> items;
    long total;
}
