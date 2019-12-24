package cn.zhengjunren.myblog.system.vo;

import cn.zhengjunren.myblog.system.domain.Menu;
import cn.zhengjunren.myblog.system.domain.Permission;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: RoleVo</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/20 15:41
 */
@Data
public class RoleVo {

    private long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 菜单
     */
    private List<Menu> menus;

    /**
     * 权限
     */
    private List<Permission> permissions;
}
