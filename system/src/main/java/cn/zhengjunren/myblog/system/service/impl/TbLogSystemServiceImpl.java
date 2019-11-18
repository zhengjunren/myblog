package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.TbLog;
import cn.zhengjunren.myblog.system.mapper.TbLogSystemMapper;
import cn.zhengjunren.myblog.system.service.TbLogSystemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbLogSystemServiceImpl implements TbLogSystemService {

    @Resource
    private TbLogSystemMapper tbLogSystemMapper;


    @Override
    public PageInfo<TbLog> page(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TbLog> logList = tbLogSystemMapper.selectAll();
        return new PageInfo<>(logList);
    }
}
