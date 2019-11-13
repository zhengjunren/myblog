package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbUser;
import cn.zhengjunren.myblog.business.dto.LoginInfo;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: UserController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 13:10
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    TbUserService tbUserService;

    @GetMapping("info")
    public ResponseResult<LoginInfo> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(authentication.getName());
        TbUser tbUser = tbUserService.getByUsername(authentication.getName());
        loginInfo.setAvatar(tbUser.getAvatar());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

}
