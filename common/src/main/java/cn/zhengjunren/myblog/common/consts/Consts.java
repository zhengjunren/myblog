package cn.zhengjunren.myblog.common.consts;

/**
 * <p>ClassName: Consts</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 21:57
 */
public interface Consts {

    /**
     * 启用
     */
    Integer ENABLE = 1;
    /**
     * 禁用
     */
    Integer DISABLE = 0;

    /**
     * 页面
     */
    Integer PAGE = 1;

    /**
     * 按钮
     */
    Integer BUTTON = 2;

    /**
     * JWT 在 Redis 中保存的key前缀
     */
    String REDIS_JWT_KEY_PREFIX = "security:jwt:";

    /**
     * 星号
     */
    String SYMBOL_STAR = "*";

    /**
     * 邮箱符号
     */
    String SYMBOL_EMAIL = "@";

    /**
     * 默认当前页码
     */
    Integer DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认每页条数
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 匿名用户 用户名
     */
    String ANONYMOUS_NAME = "匿名用户";

    /**
     * 菜单缓存名
     */
    String MENU_CACHE_NAME = "menu";

    /**
     * 权限缓存名
     */
    String PERMISSION_CACHE_NAME = "permission";

    /**
     * 用户缓存名
     */
    String USER_CACHE_NAME = "user";

    /**
     * 角色缓存名
     */
    String ROLE_CACHE_NAME = "role";

    /**
     * redis 存活时间（单位：毫秒）：3小时
     */
    long REDIS_TTL = 10800000L;

    /**
     * WebSocket常量
     */
    String PUSH_SERVER = "/topic/server";

    /**
     * INFO 日志类型
     */
    String INFO_LOG_TYPE = "INFO";

    /**
     * ERROR 日志类型
     */
    String ERROR_LOG_TYPE = "ERROR";

    /**
     * excel 字体名称
     */
    String EXCEL_FONT_NAME = "等线 Light";

    /**
     * 0 为周一
     */
    String[] WEEKDAY = {"周日","周一","周二","周三","周四","周五","周六"};
}

