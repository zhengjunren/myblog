package cn.zhengjunren.myblog.log.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>ClassName: OwnLogDTO</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/3 23:54
 */
@Data
public class OwnLogDTO {
    private String description;
    private Integer count;
    private Date createTime;
}
