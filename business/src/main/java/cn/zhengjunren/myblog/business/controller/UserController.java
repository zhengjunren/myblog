package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbUserRole;
import cn.zhengjunren.myblog.business.dto.AvatarInfo;
import cn.zhengjunren.myblog.business.dto.LoginInfo;
import cn.zhengjunren.myblog.business.dto.PasswordParams;
import cn.zhengjunren.myblog.business.dto.StatusInfo;
import cn.zhengjunren.myblog.business.dto.TbUserWithRole;
import cn.zhengjunren.myblog.business.service.TbUserRoleService;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.domain.TbUser;
import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.utils.DataTypeUtils;
import cn.zhengjunren.myblog.commons.utils.ParamTypeUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>ClassName: UserController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 13:10
 */
@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("user")
@Api(tags = "用户管理")
public class UserController {

    private final TbUserService tbUserService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    private final TbUserRoleService tbUserRoleService;

    public UserController(TbUserService tbUserService, TbUserRoleService tbUserRoleService) {
        this.tbUserService = tbUserService;
        this.tbUserRoleService = tbUserRoleService;
    }

    @GetMapping("info")
    @ApiOperation(value = "获取用户信息")
    public ResponseResult<LoginInfo> info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(authentication.getName());
        TbUser tbUser = tbUserService.getByUsername(authentication.getName());
        BeanUtils.copyProperties(tbUser, loginInfo);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    @MyLog("获取用户列表")
    @GetMapping("list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
    })
    @ApiOperation(value = "获取用户列表", notes="根据页码、笔数查询用户列表")
    public ResponseResult<ListInfo<TbUserWithRole>> list(int page, int limit) {
        PageInfo<TbUserWithRole> pageInfo = tbUserService.page(page, limit);
        ListInfo<TbUserWithRole> listInfo = new ListInfo<>(pageInfo.getList(), pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"分页获取用户列表", listInfo);
    }

    @MyLog("修改用户信息")
    @PostMapping("")
    @ApiOperation(value = "修改用户信息", notes="修改除密码外的属性")
    @ApiImplicitParam(name = "tbUser", value = "用户信息", required = true, dataType = "TbUser", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> update(@RequestBody TbUserWithRole tbUserWithRole) {
        TbUser tbUser = new TbUser();
        BeanUtils.copyProperties(tbUserWithRole, tbUser);
        tbUserRoleService.update(new TbUserRole((long)tbUserWithRole.getId(), tbUserWithRole.getRoleId()));
        int result = tbUserService.update(tbUser);
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK,"更新用户成功", null);
        }
        return  new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"网络错误", null);
    }

    @MyLog("修改用户状态")
    @PostMapping("status")
    @ApiOperation(value = "修改用户状态", notes="状态为：正常、冻结、注销")
    @ApiImplicitParam(name = "statusInfo", value = "状态", required = true, dataType = "StatusInfo", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> modifyStatus(@RequestBody StatusInfo statusInfo) {
        TbUser tbUser = tbUserService.getByUsername(statusInfo.getUsername());
        tbUser.setStatus(statusInfo.getValue());
        int result = tbUserService.update(tbUser);
        return commonResponse("更新用户状态成功", "网络错误", result);
    }

    @MyLog("修改个人信息")
    @PostMapping("profile")
    @ApiOperation(value = "修改个人信息", notes="只能修改邮件、头像、首页、昵称")
    @ApiImplicitParam(name = "tbUser", value = "个人信息", required = true, dataType = "TbUser", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> getProfile(@RequestBody TbUser tbUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser oldTbUser = tbUserService.getByUsername(authentication.getName());
        int result = tbUserService.modifyProfile(oldTbUser, tbUser);
        return commonResponse("个人信息更新成功", "网络错误", result);
    }

    /**
     * 更新头像
     * @param avatarInfo {@link AvatarInfo}
     * @return {@link ResponseResult<Void> }
     */
    @MyLog("修改个人头像")
    @PostMapping("avatar")
    @ApiOperation(value = "修改个人头像")
    @ApiImplicitParam(name = "avatarInfo", value = "头像信息", required = true, dataType = "AvatarInfo", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> modifyAvatar(@RequestBody AvatarInfo avatarInfo) {
        int result = tbUserService.modifyAvatar(avatarInfo.getUsername(), avatarInfo.getPath());
        return commonResponse("头像更新成功", "网络错误，请重新上传", result);
    }

    /**
     * 修改密码
     * @param passwordParams {@link PasswordParams}
     * @return {@link ResponseResult<Void>}
     */
    @PutMapping("password")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "passwordParams", value = "密码信息", required = true, dataType = "PasswordParams", paramType = ParamTypeUtils.BODY)
    public ResponseResult<Void> modifyPassword(@RequestBody PasswordParams passwordParams) {
        if (StringUtils.isNotBlank(passwordParams.getNewPassword()) && StringUtils.isNotBlank(passwordParams.getConfirmPassword()) && passwordParams.getNewPassword().equals(passwordParams.getConfirmPassword())){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            TbUser oldTbUser = tbUserService.getByUsername(authentication.getName());
            if (passwordEncoder.matches(passwordParams.getOldPassword(), oldTbUser.getPassword())) {
                oldTbUser.setPassword(passwordEncoder.encode(passwordParams.getNewPassword()));
                int result = tbUserService.update(oldTbUser);
                return commonResponse("密码修改成功，请重新登录", "网络错误，请重试", result);
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "原密码错误");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "两次密码不匹配");
    }

    private ResponseResult<Void> commonResponse(String successMessage, String failMessage, int result) {
        return commonResponse(successMessage, failMessage, result, 0);
    }

    private ResponseResult<Void> commonResponse(String successMessage, String failMessage, int result,int standard){
        if (result > standard) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK,successMessage, null);
        }
        return  new ResponseResult<>(ResponseResult.CodeStatus.FAIL,failMessage, null);
    }
}
