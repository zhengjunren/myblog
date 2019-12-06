package cn.zhengjunren.myblog.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: AvatarInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/14 19:21
 */
@Data
public class AvatarInfo implements Serializable {
    private static final long serialVersionUID = -5168203086804836641L;
    String username;
    String path;
}
