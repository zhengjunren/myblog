package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.service.OnlineUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("online")
public class OnlineUserController {
    public final OnlineUserService onlineUserService;

    public OnlineUserController(OnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
    }

    @GetMapping("")
    public ResponseResult<ListInfo<OnlineUser>> getAll() {
        List<OnlineUser> onlineUsers = onlineUserService.selectAll("");
        ListInfo<OnlineUser> listInfo = new ListInfo<>(onlineUsers, onlineUsers.size());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取在线用户", listInfo);
    }

    @DeleteMapping(value = "/{token}")
    public ResponseResult<Void> kickOut(@PathVariable("token") String token){
        try {
            onlineUserService.kickOut(token);
        } catch (Exception e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "网络错误");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "用户已踢出");
    }
}
