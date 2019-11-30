package cn.zhengjunren.myblog.business.service.impl;

import cn.zhengjunren.myblog.business.domain.TbArticle;
import cn.zhengjunren.myblog.business.dto.TbArticleNoContent;
import cn.zhengjunren.myblog.business.mapper.TbArticleMapper;
import cn.zhengjunren.myblog.business.service.TbArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>ClassName: TbArticleServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/29 13:18
 */

@Service
public class TbArticleServiceImpl implements TbArticleService{

    @Resource
    private TbArticleMapper tbArticleMapper;

    @Override
    public PageInfo<TbArticleNoContent> page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbArticleNoContent> articleNoContents = tbArticleMapper.selectAllNoContent();
        return new PageInfo<>(articleNoContents);
    }

    @Override
    public TbArticle getById(Integer id) {
        return tbArticleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(TbArticle tbArticle) {
        return tbArticleMapper.insert(tbArticle);
    }

    @Override
    public int update(TbArticle tbArticle) {
        return tbArticleMapper.updateByPrimaryKey(tbArticle);
    }

    @Override
    public List<TbArticleNoContent> selectLatestArticle(Integer number, String username) {
        return tbArticleMapper.selectLatestArticle(number, username);
    }
}
