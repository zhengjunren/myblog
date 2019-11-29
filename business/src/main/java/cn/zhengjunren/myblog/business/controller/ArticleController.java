package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbArticle;
import cn.zhengjunren.myblog.business.dto.ListInfo;
import cn.zhengjunren.myblog.business.dto.TbArticleNoContent;
import cn.zhengjunren.myblog.business.service.TbArticleService;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: ArticleController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 13:18
 */
@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("article")
@Api(tags = "文章管理")
public class ArticleController {

    private final TbArticleService tbArticleService;


    public ArticleController(TbArticleService tbArticleService) {
        this.tbArticleService = tbArticleService;
    }

    @GetMapping("list")
    public ResponseResult<ListInfo<TbArticleNoContent>> list(int page, int limit) {
        PageInfo<TbArticleNoContent> pageInfo = tbArticleService.page(page, limit);
        ListInfo<TbArticleNoContent> listInfo = new ListInfo<>(pageInfo.getList(), pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"分页获取文章列表", listInfo);
    }

    @GetMapping("{id}")
    public ResponseResult<TbArticle> get(@PathVariable Integer id) {
        TbArticle tbArticle = tbArticleService.getById(id);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"获取文章", tbArticle);
    }
}
