package cn.zhengjunren.myblog.security.enums;

/**
 * <p>ClassName: StatusEnum</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/14 10:47
 */
public enum StatusEnum {
    /**
     * 注释
     */
    NORMAL("正常"),
    /**
     *
     */
    FREEZE("冻结"),
    /**
     *
     */
    CANCELLATION("注销");

    private final String value;

    /**
     *
     * @param value 值
     */
    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
