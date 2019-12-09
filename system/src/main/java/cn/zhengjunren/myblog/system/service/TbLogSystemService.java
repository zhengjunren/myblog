package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import com.github.pagehelper.PageInfo;

import java.sql.Timestamp;
import java.util.List;

public interface TbLogSystemService{

    /**
     * 分页查询
     * @param pageNum 页码
     * @param pageSize 每页显示的数量
     * @return {@link List <TbLog>}
     */
    PageInfo<TbLog> page(Integer pageNum, Integer pageSize, Timestamp start, Timestamp end);

    /**
     * 获取操作日志次数
     * @return {@link Integer}
     */
    Integer count();

}
