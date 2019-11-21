package cn.zhengjunren.myblog.system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>ClassName: EmailForm</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/21 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailForm {

    private List<String> tos;

    private String subject;

    private String content;

}
