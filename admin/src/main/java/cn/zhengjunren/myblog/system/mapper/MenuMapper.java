package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.common.mapper.MyMapper;
import cn.zhengjunren.myblog.system.domain.Menu;
import cn.zhengjunren.myblog.system.dto.MenuDTO;
import cn.zhengjunren.myblog.common.consts.Consts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

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
    @Cacheable(cacheNames = Consts.MENU_CACHE_NAME, key = "#p0")
    List<Menu> findByRolesIdAndTypeIsNotInOrderBySortAsc(@Param("id") Long id, @Param("type") Integer type);

    /**
     * 根据角色id查找菜单
     * @param roleId 角色id
     * @return 角色拥有的菜单
     */
    List<Menu> selectByRoleId(@Param("roleId") long roleId);

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

    /**
     *  将 实体list 转化为 DTO list
     * @param entityList 实体list
     * @return {@link List<MenuDTO>}
     */
    @Override
    default List<MenuDTO> toDto(List<Menu> entityList) {
        if ( entityList == null ) {
            return null;
        }
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }


}
