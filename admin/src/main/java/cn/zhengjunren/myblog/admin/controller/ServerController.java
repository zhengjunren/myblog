package cn.zhengjunren.myblog.admin.controller;

import cn.hutool.core.lang.Dict;
import cn.zhengjunren.myblog.admin.domain.Server;
import cn.zhengjunren.myblog.admin.utils.ServerUtil;
import cn.zhengjunren.myblog.admin.vo.sever.ServerVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: ServerController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 18:07
 */
@RestController
@RequestMapping("/api/server")
public class ServerController {

    @GetMapping
    public Dict serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        return ServerUtil.wrapServerDict(serverVO);
    }

}
