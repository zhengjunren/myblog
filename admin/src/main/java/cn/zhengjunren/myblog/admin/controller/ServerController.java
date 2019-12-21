package cn.zhengjunren.myblog.admin.controller;

import cn.hutool.core.lang.Dict;
import cn.zhengjunren.myblog.admin.domain.Server;
import cn.zhengjunren.myblog.admin.utils.ServerUtil;
import cn.zhengjunren.myblog.admin.vo.sever.ServerVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>ClassName: ServerController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 18:07
 */
@Controller
@RequestMapping("/api/server")
public class ServerController {

    @Value("${myblog.host}")
    private String host;

    @Value("${myblog.js}")
    private  String js;

    @GetMapping
    @ResponseBody
    public Dict serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        return ServerUtil.wrapServerDict(serverVO);
    }

    @GetMapping("index")
    public String server(Model model) {
        model.addAttribute("host", host);
        model.addAttribute("js", js);
        return "server";
    }
}
