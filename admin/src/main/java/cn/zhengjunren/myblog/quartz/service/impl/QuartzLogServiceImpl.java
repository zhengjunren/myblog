package cn.zhengjunren.myblog.quartz.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zhengjunren.myblog.quartz.domain.QuartzLog;
import cn.zhengjunren.myblog.quartz.mapper.QuartzLogMapper;
import cn.zhengjunren.myblog.quartz.service.QuartzLogService;
@Service
public class QuartzLogServiceImpl extends ServiceImpl<QuartzLogMapper, QuartzLog> implements QuartzLogService{

}
