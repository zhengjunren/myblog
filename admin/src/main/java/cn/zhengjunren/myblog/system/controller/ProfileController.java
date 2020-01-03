package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.log.dto.OwnLogDTO;
import cn.zhengjunren.myblog.log.service.LogService;
import cn.zhengjunren.myblog.security.utils.SecurityUtil;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import cn.zhengjunren.myblog.system.dto.params.PasswordParams;
import cn.zhengjunren.myblog.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public ProfileController(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
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

    @PutMapping("/avatar")
    public ApiResponse updateAvatar(String avatar) {
        userService.updateAvatarByUsername(get(), avatar);
        return ApiResponse.ofSuccess();
    }

    @PutMapping("password")
    public ApiResponse updatePassword(@RequestBody PasswordParams passwordParams) {
        userService.updatePassword(get(), passwordParams);
        return ApiResponse.ofSuccess();
    }

    private String get() {
        return SecurityUtil.getCurrentUsername();
    }

}
