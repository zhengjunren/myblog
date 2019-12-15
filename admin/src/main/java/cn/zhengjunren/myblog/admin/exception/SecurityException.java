package cn.zhengjunren.myblog.admin.exception;

import cn.zhengjunren.myblog.admin.common.BaseException;
import cn.zhengjunren.myblog.admin.common.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>ClassName: SecurityException</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 21:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityException extends BaseException {
    public SecurityException(Status status) {
        super(status);
    }

    public SecurityException(Status status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
