package cn.zhengjunren.myblog.system.dto;

import cn.zhengjunren.myblog.system.domain.TbLog;
import lombok.Data;

import java.util.List;

/**
 * <p>ClassName: LogListInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 20:34
 */
@Data
public class LogListInfo {
    List<TbLog> items;
    long total;
}
