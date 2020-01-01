package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ZhengJunren
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "qiniu_content")
public class QiniuContent extends BaseDomain implements Serializable {
    /**
     * Bucket 识别符
     */
    @ExcelProperty("空间名")
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 文件名称
     */
    @ExcelProperty("文件名称")
    @TableField(value = "`name`")
    private String name;

    /**
     * 文件大小
     */
    @ExcelProperty("文件大小")
    @TableField(value = "size")
    private String size;

    /**
     * 文件类型：私有或公开
     */
    @ExcelProperty("文件类型")
    @TableField(value = "type")
    private String type;

    /**
     * 上传或同步的时间
     */
    @ExcelProperty("同步的时间")
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 文件url
     */
    @ExcelProperty("文件url")
    @TableField(value = "url")
    private String url;

    @ExcelProperty("文件类型")
    @TableField(value = "suffix")
    private String suffix;

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    public static final String COL_BUCKET = "bucket";

    @ExcelIgnore
    public static final String COL_NAME = "`name`";

    @ExcelIgnore
    public static final String COL_SIZE = "size";

    @ExcelIgnore
    public static final String COL_TYPE = "type";

    @ExcelIgnore
    public static final String COL_UPDATE_TIME = "update_time";

    @ExcelIgnore
    public static final String COL_URL = "url";

    @ExcelIgnore
    public static final String COL_SUFFIX = "suffix";
}

