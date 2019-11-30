package cn.zhengjunren.myblog.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>ClassName: Status</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/14 12:31
 */
@Data
public class StatusInfo implements Serializable {
    String value;
    String username;
}
