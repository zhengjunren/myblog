package cn.zhengjunren.myblog.search.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: UserSearchDTO</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/15 21:18
 */
@Data
public class UserSearchParams implements Serializable {

    private static final long serialVersionUID = 6633463968331728227L;
    private String username;
    private String nickname;
    private String status;
    private String email;
}
