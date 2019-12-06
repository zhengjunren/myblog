package cn.zhengjunren.myblog.search.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: RoleAndUrl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/23 11:36
 */
@Data
public class RoleAndUrl implements Serializable {
    private static final long serialVersionUID = 3756224214961822522L;
    String url;

    String enname;
}
