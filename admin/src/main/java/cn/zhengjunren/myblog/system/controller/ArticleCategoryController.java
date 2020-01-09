package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.system.service.ArticleCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
