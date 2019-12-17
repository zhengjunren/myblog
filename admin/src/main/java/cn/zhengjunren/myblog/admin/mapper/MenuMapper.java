package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.common.MyMapper;
import cn.zhengjunren.myblog.admin.domain.Menu;
import cn.zhengjunren.myblog.admin.dto.MenuDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu>, MyMapper<MenuDTO, Menu> {

    List<Menu> findByRolesIdAndTypeIsNotInOrderBySortAsc(@Param("id") Long id, @Param("type") Integer type);

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
