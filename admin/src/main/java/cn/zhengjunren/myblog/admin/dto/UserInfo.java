package cn.zhengjunren.myblog.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: UserInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/16 11:13
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 4188499583571417878L;

    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户角色列表
     */
    private List<String> roles;
}