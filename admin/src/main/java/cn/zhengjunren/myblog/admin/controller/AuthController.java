package cn.zhengjunren.myblog.admin.controller;


import cn.zhengjunren.myblog.admin.dto.params.LoginParams;
import cn.zhengjunren.myblog.admin.dto.info.UserInfo;
import cn.zhengjunren.myblog.common.exception.SecurityException;
import cn.zhengjunren.myblog.admin.utils.JwtUtil;
import cn.zhengjunren.myblog.admin.utils.SecurityUtil;
import cn.zhengjunren.myblog.admin.vo.JwtResponse;
import cn.zhengjunren.myblog.admin.vo.UserPrincipal;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * <p>ClassName: AuthController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 22:17
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录
     * @param loginRequest {@link LoginParams}
     * @return 返回带 token 的数据
     */
    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginParams loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication,loginRequest.getRememberMe());
        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    /**
     * 退出
     * @param request 请求头中带有 token
     * @return 退出成功
     */
    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(Status.UNAUTHORIZED);
        }
        return ApiResponse.ofStatus(Status.LOGOUT);
    }

    /**
     * 根据 token 获取前端存储在 vuex 中的用户信息
     * @return 用户信息
     */
    @GetMapping("info")
    public ApiResponse getUserInfo() {
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        assert currentUser != null;
        BeanUtils.copyProperties(currentUser, userInfo);
        userInfo.setName(currentUser.getUsername());
        return ApiResponse.ofSuccess(userInfo);
    }
}

