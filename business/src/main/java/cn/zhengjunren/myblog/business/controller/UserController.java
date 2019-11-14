package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbUser;
import cn.zhengjunren.myblog.business.dto.LoginInfo;
import cn.zhengjunren.myblog.business.dto.Status;
import cn.zhengjunren.myblog.business.dto.UserListInfo;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin(origins = "*", maxAge = 3600L)
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
        BeanUtils.copyProperties(tbUser, loginInfo);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    @GetMapping("list")
    public ResponseResult<UserListInfo> list(Integer page, Integer limit) {
        PageInfo<TbUser> pageInfo = tbUserService.page(page, limit);
        UserListInfo userListInfo = new UserListInfo();
        userListInfo.setItems(pageInfo.getList());
        userListInfo.setTotal(pageInfo.getTotal());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK,"分页获取用户列表", userListInfo);
    }

    @PostMapping("")
    public ResponseResult<Void> update(@RequestBody TbUser tbUser) {
        int result = tbUserService.update(tbUser);
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK,"更新用户成功", null);
        }
        return  new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"网络错误", null);
    }

    @PostMapping("status")
    public ResponseResult<TbUser> modifyStatus(@RequestBody Status status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser tbUser = tbUserService.getByUsername(authentication.getName());
        tbUser.setStatus(status.getValue());
        int result = tbUserService.update(tbUser);
        if (result > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK,"更新用户状态成功", null);
        }
        return  new ResponseResult<>(ResponseResult.CodeStatus.FAIL,"网络错误", null);
    }

    @PostMapping("profile")
    public ResponseResult<Void> getProfile(@RequestBody TbUser tbUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TbUser oldTbUser = tbUserService.getByUsername(authentication.getName());
        int result = tbUserService.modifyProfile(oldTbUser, tbUser);
        return commonResponse("个人信息更新成功", "网络错误", result);
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
