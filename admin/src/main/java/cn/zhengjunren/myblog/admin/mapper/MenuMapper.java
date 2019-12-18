package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.common.MyMapper;
import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author ZhengJunren
 */
public interface MenuMapper extends BaseMapper<Menu>, MyMapper<MenuDTO, Menu> {

    /**
     * 根据角色 id 和类型查找菜单
     * @param id 角色 id
     * @param type 类型
     * @return {@link List<Menu>}
     */
    List<Menu> findByRolesIdAndTypeIsNotInOrderBySortAsc(@Param("id") Long id, @Param("type") Integer type);

    /**
     * 将 Menu 类型转为 MenuDto
     * @param entity Menu 类实体
     * @return MenuDto 类实体
     */
    @Override
    default MenuDTO toDto(Menu entity){
        if ( entity == null ) {
            return null;
        }
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(entity, menuDTO);
        return menuDTO;
    }
}
