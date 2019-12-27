package cn.zhengjunren.myblog.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ClassName: PageUtil</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 15:38
 */
public class PageUtil extends cn.hutool.core.util.PageUtil {
    /**
     * List 分页
     */
    public static List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }
}
