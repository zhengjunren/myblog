package cn.zhengjunren.myblog.commons.log.service.impl;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.mapper.TbLogMapper;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>ClassName: TbLogServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 10:15
 */

@Service
public class TbLogServiceImpl implements TbLogService {

    @Resource
    private TbLogMapper tbLogMapper;

    @Override
    public int save(TbLog tbLog) {
        tbLog.setCreateTime(new Date());
        tbLogMapper.insert(tbLog);
        return 0;
    }
}
