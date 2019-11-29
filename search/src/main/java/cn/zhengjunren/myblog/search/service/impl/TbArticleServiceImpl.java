package cn.zhengjunren.myblog.search.service.impl;

import cn.zhengjunren.myblog.search.domain.TbArticle;
import cn.zhengjunren.myblog.search.mapper.TbArticleMapper;
import cn.zhengjunren.myblog.search.service.TbArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * <p>ClassName: TbArticleServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 23:32
 */
@Service
public class TbArticleServiceImpl implements TbArticleService{

    @Resource
    private TbArticleMapper tbArticleMapper;

    @Override
    public List<TbArticle> selectAll() {
        return tbArticleMapper.selectAll();
    }
}
