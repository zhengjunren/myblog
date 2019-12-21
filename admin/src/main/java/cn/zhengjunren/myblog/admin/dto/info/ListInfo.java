package cn.zhengjunren.myblog.admin.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>ClassName: ListInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListInfo implements Serializable {

    private static final long serialVersionUID = -2583330880143899842L;

    Object items;

    long total;
}
