package cn.zhengjunren.myblog.admin.mapper;

import cn.zhengjunren.myblog.admin.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(@Param("userId") Long userId);
}
