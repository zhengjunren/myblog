package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import cn.zhengjunren.myblog.commons.dto.ListInfo;
import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.utils.DataTypeUtils;
import cn.zhengjunren.myblog.commons.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.system.service.OnlineUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "在线用户管理")
public class OnlineUserController {
    public final OnlineUserService onlineUserService;

    public OnlineUserController(OnlineUserService onlineUserService) {
        this.onlineUserService = onlineUserService;
    }

    @GetMapping("list")
    @MyLog("获取在线用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filter", value = "过滤条件", required = true, dataType = DataTypeUtils.STRING, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.INT, paramType = ParamTypeUtils.QUERY),
    })
    public ResponseResult<ListInfo<OnlineUser>> getAll(String filter, int page, int limit) {
        Pageable pageable = PageRequest.of(page-1, limit);
        Page<OnlineUser> pageInfo = onlineUserService.page(filter, pageable);
        ListInfo<OnlineUser> listInfo = new ListInfo<>(pageInfo.getContent(), pageInfo.getTotalElements());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取在线用户", listInfo);
    }

    @DeleteMapping(value = "/{token}")
    @ApiOperation(value = "踢出用户")
    @ApiImplicitParam(name = "token", value = "加密后的 token", required = true, dataType = DataTypeUtils.STRING, paramType = ParamTypeUtils.PATH)
    public ResponseResult<Void> kickOut(@PathVariable("token") String token){
        try {
            onlineUserService.kickOut(token);
        } catch (Exception e) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "网络错误");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "用户已踢出");
    }
}
