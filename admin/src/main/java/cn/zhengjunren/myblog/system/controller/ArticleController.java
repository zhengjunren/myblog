package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.common.utils.DataTypeUtils;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.security.utils.SecurityUtil;
import cn.zhengjunren.myblog.system.domain.Article;
import cn.zhengjunren.myblog.system.dto.condition.ArticleQueryCondition;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import cn.zhengjunren.myblog.system.service.ArticleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    @PostMapping
    @MyLog("发布文章")
    @ApiOperation(value = "发布文章")
    @ApiImplicitParam(name = "entity", value = "实体", required = true, dataType = "Article", paramType = ParamTypeUtils.BODY)
    public ApiResponse create(@RequestBody Article entity) {
        if (entity.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        entity.setUserId(Objects.requireNonNull(SecurityUtil.getCurrentUser()).getId());
        entity.setCreateTime(new Date());
        service.save(entity);
        return ApiResponse.ofSuccess();
    }

    @PutMapping
    public ApiResponse update(@RequestBody Article article) {
        article.setUpdateTime(new Date());
        service.saveOrUpdate(article);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据 id 查询文章")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.PATH)
    public ApiResponse get(@PathVariable Integer id) {
        Article tbArticle = service.getById(id);
        return ApiResponse.ofSuccess(tbArticle);
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
