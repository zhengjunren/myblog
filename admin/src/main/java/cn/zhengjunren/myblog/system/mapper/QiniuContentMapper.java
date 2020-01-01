package cn.zhengjunren.myblog.system.mapper;

import cn.zhengjunren.myblog.system.domain.QiniuContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZhengJunren
 */
public interface QiniuContentMapper extends BaseMapper<QiniuContent> {

    QiniuContent findByKey(@Param("key") String key);
}
