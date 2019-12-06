package cn.zhengjunren.myblog.business.domain;

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
    private static final long serialVersionUID = -8059658897182757963L;
    String url;

    String enname;
}
