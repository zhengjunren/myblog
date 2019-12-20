package cn.zhengjunren.myblog.common.exception;

import cn.zhengjunren.myblog.common.staus.Status;

/**
 * <p>ClassName: BadRequestException</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/19 15:35
 */
public class BadRequestException extends BaseException {
    public BadRequestException(Status status){
        super(status);
    }

    public BadRequestException(Status status, Object data) {
        super(status, data);
    }

    public BadRequestException(Integer code, String message) {
        super(code, message);
    }

    public BadRequestException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
