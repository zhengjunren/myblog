package cn.zhengjunren.myblog.admin.vo.sever;

import cn.zhengjunren.myblog.admin.domain.server.Sys;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统相关信息实体VO
 * </p>
 *
 * @package: com.xkcoding.websocket.payload.server
 * @description: 系统相关信息实体VO
 * @author: yangkai.shen
 * @date: Created in 2018-12-14 17:28
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
public class SysVO {
    List<KV> data = Lists.newArrayList();

    public static SysVO create(Sys sys) {
        SysVO vo = new SysVO();
        vo.data.add(new KV("服务器名称", sys.getComputerName()));
        vo.data.add(new KV("服务器Ip", sys.getComputerIp()));
        vo.data.add(new KV("项目路径", sys.getUserDir()));
        vo.data.add(new KV("操作系统", sys.getOsName()));
        vo.data.add(new KV("系统架构", sys.getOsArch()));
        return vo;
    }
}
