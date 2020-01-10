package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.system.domain.Tag;
import cn.zhengjunren.myblog.system.mapper.TagMapper;
import cn.zhengjunren.myblog.system.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * @author ZhengJunren
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService{

}
