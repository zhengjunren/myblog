package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.system.mapper.TbLogSystemMapper;
import cn.zhengjunren.myblog.system.service.TbLogSystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TbLogSystemServiceImpl implements TbLogSystemService {

    @Resource
    private TbLogSystemMapper tbLogSystemMapper;


    @Override
    public PageInfo<TbLog> page(Integer pageNum, Integer pageSize, Timestamp start, Timestamp end) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(TbLog.class);
        example.createCriteria().andBetween("createTime", start, end);
        List<TbLog> logList = tbLogSystemMapper.selectByExample(example);
        return new PageInfo<>(logList);
    }

    @Override
    public Integer count() {
        return tbLogSystemMapper.selectCount(null);
    }

    @Override
    public List<TbLog> selectAll(Timestamp start, Timestamp end) {

        return null;
    }
}
