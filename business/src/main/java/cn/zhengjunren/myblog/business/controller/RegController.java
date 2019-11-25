package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbUser;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.utils.ParamTypeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: RegController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 10:13
 */

@RestController
@RequestMapping("reg")
@Api(tags = "用户注册")
public class RegController {

    @Autowired
    TbUserService tbUserService;

    @MyLog("用户注册")
    @PostMapping()
    @ApiOperation(value = "用户注册", notes="email、username、password不能为空")
    @ApiImplicitParam(name = "tbUser", value = "用户信息", required = true, dataType = "TbUser", paramType = ParamTypeUtils.BODY)
    public ResponseResult<TbUser> reg(@RequestBody TbUser tbUser) {
        String validateRegResult = validateReg(tbUser);
        //验证通过
        if (validateRegResult == null) {
            int result = tbUserService.insert(tbUser);
            if (result > 0){
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "用户注册成功", tbUser);
            }
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL,validateRegResult != null ? validateRegResult : "用户注册失败！");
    }

    /**
     * 注册信息验证
     * @param tbUser {@link TbUser}
     * @return 验证结果
     */
    private String validateReg(TbUser tbUser) {
        TbUser user = tbUserService.getByUsername(tbUser.getUsername());
        //已经有这个用户名了，返回值不为空
        if (user != null) {
            return "用户名重复，请重试！";
        }
        return null;
    }
}
