package cn.zhengjunren.myblog.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class ListInfo<T> implements Serializable {
    private static final long serialVersionUID = 5062188588707934314L;
    List<T> items;
    long total;
}
