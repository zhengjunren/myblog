package cn.zhengjunren.myblog.common.exception;

import org.springframework.util.StringUtils;
/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
public class EntityExistException extends BaseException {

    private static final long serialVersionUID = -2612596346828624977L;

    public EntityExistException(Class clazz, String field, String val) {
        super(400, EntityExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " 的" + field + "的值：“"+ val + "” 已经存在，请重新修改";
    }
}
