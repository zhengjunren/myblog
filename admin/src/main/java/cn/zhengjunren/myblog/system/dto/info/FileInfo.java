package cn.zhengjunren.myblog.system.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>ClassName: FileInfo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {
    private static final long serialVersionUID = -1656644123721134509L;
    /**
     * 文件路径
     */
    private String path;
}
