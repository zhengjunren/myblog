package cn.zhengjunren.myblog.security.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.utils.MapperUtils;
import cn.zhengjunren.myblog.commons.utils.OkHttpClientUtil;
import cn.zhengjunren.myblog.security.domain.TbUser;
import cn.zhengjunren.myblog.security.dto.LoginInfo;
import cn.zhengjunren.myblog.security.dto.LoginParam;
import cn.zhengjunren.myblog.security.enums.StatusEnum;
import cn.zhengjunren.myblog.security.service.TbUserService;
import okhttp3.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//import okhttp3.Response;

/**
 * <p>ClassName: LoginController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:59
 */
@RestController
@CrossOrigin
public class LoginController {

    private static final String URL_OAUTH_TOKEN = "http://localhost:8000/oauth/token";
    @Value("${business.oauth2.grant_type}")
    public String oauth2GrantType;
    @Value("${business.oauth2.client_id}")
    public String oauth2ClientId;
    @Value("${business.oauth2.client_secret}")
    public String oauth2ClientSecret;
    @Resource(name = "userDetailsServiceBean")
    public UserDetailsService userDetailsService;
    @Resource
    public BCryptPasswordEncoder passwordEncoder;
    @Resource
    public TokenStore tokenStore;
    @Autowired
    TbUserService tbUserService;
    /**
     * 登录
     *
     * @param loginParam 登录参数
     * @return {@link ResponseResult}
     */
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        // 封装返回的结果集
        Map<String, Object> result = new HashMap<>();
        // 验证密码是否正确
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new  ResponseResult<>(ResponseResult.CodeStatus.FAIL, "账号或密码错误", null);
        }
        TbUser tbUser = tbUserService.getByUsername(loginParam.getUsername());
        if (!tbUser.getStatus().equals(StatusEnum.NORMAL.getValue())) {
            return new  ResponseResult<>(ResponseResult.CodeStatus.STATUS_ERROR, String.format("你的账号%s了，请联系管理员", tbUser.getStatus()), null);
        }
        // 通过 HTTP 客户端请求登录接口
        Map<String, String> params = new HashMap<>();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", oauth2GrantType);
        params.put("client_id", oauth2ClientId);
        params.put("client_secret", oauth2ClientSecret);
        ResponseResult<Map<String, Object>> responseResult = null;
        try {
            // 解析响应结果封装并返回
            Response response = OkHttpClientUtil.getInstance().postData(URL_OAUTH_TOKEN, params);
            String jsonString = Objects.requireNonNull(response.body()).string();
            Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));
            result.put("token", token);
            responseResult = new ResponseResult<>(ResponseResult.CodeStatus.OK, "登录成功", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    @PostMapping("/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        String token = request.getParameter("access_token");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "注销成功", null);
    }

    @GetMapping("info")
    public ResponseResult<LoginInfo> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(authentication.getName());
        TbUser tbUser = tbUserService.getByUsername(authentication.getName());
        BeanUtils.copyProperties(tbUser, loginInfo);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

}
