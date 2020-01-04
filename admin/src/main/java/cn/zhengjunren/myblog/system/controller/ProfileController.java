package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.log.dto.OwnLogDTO;
import cn.zhengjunren.myblog.log.service.LogService;
import cn.zhengjunren.myblog.security.service.OnlineService;
import cn.zhengjunren.myblog.security.utils.JwtUtil;
import cn.zhengjunren.myblog.security.utils.SecurityUtil;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import cn.zhengjunren.myblog.system.domain.User;
import cn.zhengjunren.myblog.system.dto.params.PasswordParams;
import cn.zhengjunren.myblog.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>ClassName: ProfileController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/3 15:15
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final LogService logService;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final OnlineService onlineService;

    public ProfileController(LogService logService, UserService userService, JwtUtil jwtUtil, OnlineService onlineService) {
        this.logService = logService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.onlineService = onlineService;
    }


    @GetMapping
    public ApiResponse getOwnInfo() {
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        return ApiResponse.ofSuccess(currentUser);
    }

    @GetMapping("/log/{number}")
    public ApiResponse getOwnLog(@PathVariable("number") int number) {
        List<OwnLogDTO> ownLogDTOS = logService.selectDetailByUsername(get(), number);
        return ApiResponse.ofSuccess(ownLogDTOS);
    }

    @MyLog("修改头像")
    @PutMapping("/avatar")
    public ApiResponse updateAvatar(String avatar) {
        userService.updateAvatarByUsername(get(), avatar);
        return ApiResponse.ofSuccess();
    }

    @MyLog("修改密码")
    @PutMapping("password")
    public ApiResponse updatePassword(@Valid @RequestBody PasswordParams passwordParams, HttpServletRequest request) {
        //修改密码
        userService.updatePassword(get(), passwordParams);
        //删除在线用户
        onlineService.kickOutSelf(jwtUtil.getJwtFromRequest(request));
        //invalidate
        jwtUtil.invalidateJWT(request);
        return ApiResponse.ofSuccess();
    }


    @MyLog("更新个人信息")
    @PutMapping
    public ApiResponse update(@RequestBody User user) {
        userService.update(get(), user);
        return ApiResponse.ofSuccess();
    }

    private String get() {
        return SecurityUtil.getCurrentUsername();
    }

}
