package cn.zhengjunren.myblog.business.dto;

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
 * @date 2019/8/26 21:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {
    /**
     * 文件路径
     */
    private String path;
}
