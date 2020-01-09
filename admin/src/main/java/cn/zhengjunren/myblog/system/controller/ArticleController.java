package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.system.domain.Article;
import cn.zhengjunren.myblog.system.dto.condition.ArticleQueryCondition;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import cn.zhengjunren.myblog.system.service.ArticleService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>ClassName: ArticleController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/8 20:14
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleController extends BaseController<Article, ArticleService, ArticleQueryCondition> {

    private final ArticleCategoryService articleCategoryService;

    public ArticleController(ArticleService service, ArticleCategoryService articleCategoryService) {
        super(service);
        this.articleCategoryService = articleCategoryService;
    }

    @Override
    public ApiResponse page(ArticleQueryCondition condition) {
        if (condition.getCategoryId() != null) {
            List<Long> childrenIds = articleCategoryService.getChildrenIds(condition.getCategoryId());
            return ApiResponse.ofSuccess(service.page(condition, childrenIds));
        }
        else {
            return ApiResponse.ofSuccess(service.page(condition, null));
        }
    }

    @PutMapping("/comment/{id}")
    public ApiResponse updateComment(@PathVariable Long id) {
        service.updateComment(id);
        return ApiResponse.ofSuccess();
    }

    @Override
    public <T> void exportExcel(HttpServletResponse response) {
        throw new BadRequestException(Status.NOT_ALLOW_ACCESS);
    }
}
