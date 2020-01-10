package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.system.domain.ArticleCategory;
import cn.zhengjunren.myblog.system.dto.ArticleCategoryDTO;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: ArticleCategoryController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/9 15:38
 */
@RestController
@RequestMapping("/api/article/categories")
public class ArticleCategoryController {

    private final ArticleCategoryService articleCategoryService;

    public ArticleCategoryController(ArticleCategoryService articleCategoryService) {
        this.articleCategoryService = articleCategoryService;
    }

    @GetMapping("/tree")
    public ApiResponse getTree() {
        return ApiResponse.ofSuccess(articleCategoryService.getMenuTree(articleCategoryService.findByParentId(0L)));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取树形菜单列表")
    public ApiResponse getMenus() {
        List<ArticleCategoryDTO> list = articleCategoryService.getAll();
        return ApiResponse.ofSuccess(articleCategoryService.buildTree(list));
    }

    @PostMapping
    public ApiResponse create(@RequestBody ArticleCategory articleCategory) {
        if (articleCategory.getId() != null) {
            throw new BadRequestException(Status.ENTITY_CANNOT_HAVE_AN_ID);
        }
        articleCategory.setCreateTime(new Date());
        return ApiResponse.ofSuccess();
    }

    @PutMapping
    public ApiResponse update(@RequestBody ArticleCategory articleCategory) {
        articleCategory.setUpdateTime(new Date());
        articleCategoryService.saveOrUpdate(articleCategory);
        return ApiResponse.ofSuccess();
    }
}
