package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.TbRole;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TbRoleService{

    /**
     * 获取所有角色
     * @return {@link List<TbRole>}
     */
    List<TbRole> selectAll();

    /**
     * 分页获取文章
     * @param pageNum 页数
     * @param pageSize 笔数
     * @return {@link TbRole}
     */
    PageInfo<TbRole> page(Integer pageNum, Integer pageSize);
}
