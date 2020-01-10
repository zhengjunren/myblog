package cn.zhengjunren.myblog.system.service.mapper;

import cn.zhengjunren.myblog.common.mapper.MyMapper;
import cn.zhengjunren.myblog.system.domain.Menu;
import cn.zhengjunren.myblog.system.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>ClassName: MenuMapperStruct</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/10 16:56
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapperStruct extends MyMapper<MenuDTO, Menu> {
}
