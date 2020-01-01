package cn.zhengjunren.myblog.system.domain;

import cn.zhengjunren.myblog.common.domain.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ZhengJunren
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "qiniu_config")
public class QiniuConfig extends BaseDomain implements Serializable {
    /**
     * accessKey
     */
    @TableField(value = "access_key")
    private String accessKey;

    /**
     * Bucket 识别符
     */
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 外链域名
     */
    @TableField(value = "host")
    private String host;

    /**
     * secretKey
     */
    @TableField(value = "secret_key")
    private String secretKey;

    /**
     * 空间类型
     */
    @TableField(value = "type")
    private String type;

    /**
     * 机房
     */
    @TableField(value = "zone")
    private String zone;

    private static final long serialVersionUID = 1L;

    public static final String COL_ACCESS_KEY = "access_key";

    public static final String COL_BUCKET = "bucket";

    public static final String COL_HOST = "host";

    public static final String COL_SECRET_KEY = "secret_key";

    public static final String COL_TYPE = "type";

    public static final String COL_ZONE = "zone";
}
