package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.OnlineUser;
import cn.zhengjunren.myblog.system.service.OnlineUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>ClassName: OnlineUserController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/7 10:32
 */
@RestController
public class OnlineUserController {
    public final OnlineUserService onlineUserService;

    public OnlineUserController(OnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
    }

    @GetMapping("online")
    public ResponseResult<List<OnlineUser>> getAll() {
        List<OnlineUser> onlineUsers = onlineUserService.selectAll("");
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取在线用户", onlineUsers);
    }
}
