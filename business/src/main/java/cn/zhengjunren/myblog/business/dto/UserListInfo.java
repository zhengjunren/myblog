package cn.zhengjunren.myblog.business.dto;

import cn.zhengjunren.myblog.business.domain.TbUser;
import lombok.Data;

import java.util.List;

/**
 * <p>ClassName: UserListInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/13 23:14
 */
@Data
public class UserListInfo {
    List<TbUser> items;
    long total;
}
