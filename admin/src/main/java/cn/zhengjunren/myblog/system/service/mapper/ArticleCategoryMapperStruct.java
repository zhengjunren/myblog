package cn.zhengjunren.myblog.system.service.mapper;

import cn.zhengjunren.myblog.common.mapper.MyMapper;
import cn.zhengjunren.myblog.system.domain.ArticleCategory;
import cn.zhengjunren.myblog.system.dto.ArticleCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * <p>ClassName: ArticleCategoryMapperStruct</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/10 15:38
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticleCategoryMapperStruct extends MyMapper<ArticleCategoryDTO, ArticleCategory> {

}
