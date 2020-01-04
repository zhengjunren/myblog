package cn.zhengjunren.myblog.security.controller;


import cn.hutool.core.util.IdUtil;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.exception.SecurityException;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.staus.Status;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.common.utils.RedisUtil;
import cn.zhengjunren.myblog.security.service.OnlineService;
import cn.zhengjunren.myblog.security.utils.JwtUtil;
import cn.zhengjunren.myblog.security.utils.SecurityUtil;
import cn.zhengjunren.myblog.security.vo.JwtResponse;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import cn.zhengjunren.myblog.system.dto.info.UserInfo;
import cn.zhengjunren.myblog.system.dto.params.LoginParams;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
@Api(tags = "登录授权")
public class AuthController {

    private final static String CODE_KEY = "code-key:";

    private final AuthenticationManager authenticationManager;

    private final OnlineService onlineService;

    private final RedisUtil redisUtil;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, OnlineService onlineService, RedisUtil redisUtil, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.onlineService = onlineService;
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录
     * @param loginParams {@link LoginParams}
     * @return 返回带 token 的数据
     */
    @MyLog("用户登录")
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes="用户名、邮箱、手机")
    @ApiImplicitParam(name = "loginParams", value = "登录参数", required = true, dataType = "LoginParams", paramType = ParamTypeUtils.BODY)
    public ApiResponse login(@Valid @RequestBody LoginParams loginParams, HttpServletRequest request) {
        String code = (String) redisUtil.get(loginParams.getUuid());
        redisUtil.del(loginParams.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException(400, "验证码不存在或已过期");
        }
        if (StringUtils.isBlank(loginParams.getCode()) || !loginParams.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException(400, "验证码错误");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginParams.getUsernameOrEmailOrPhone(), loginParams.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication,loginParams.getRememberMe());
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        onlineService.save(currentUser, request, jwt);
        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    @GetMapping(value = "/code")
    @ApiOperation(value = "获取验证码")
    public ApiResponse getCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha (111, 36);
        captcha.setLen(2);
        String result = captcha.text();
        String uuid = CODE_KEY + IdUtil.simpleUUID();
        redisUtil.set(uuid, result, 2, TimeUnit.MINUTES);
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ApiResponse.ofSuccess(imgResult);
    }

    /**
     * 退出
     * @param request 请求头中带有 token
     * @return 退出成功
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户注销", notes="需要携带token")
    public ApiResponse logout(HttpServletRequest request) {
        try {
            String jwt = jwtUtil.getJwtFromRequest(request);
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
            onlineService.kickOutSelf(jwt);
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
    @ApiOperation(value = "获取用户信息")
    public ApiResponse getUserInfo() {
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        assert currentUser != null;
        BeanUtils.copyProperties(currentUser, userInfo);
        userInfo.setName(currentUser.getUsername());
        return ApiResponse.ofSuccess(userInfo);
    }
}

