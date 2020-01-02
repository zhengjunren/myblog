package cn.zhengjunren.myblog.security.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.utils.DataTypeUtils;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.security.dto.OnlineQueryCondition;
import cn.zhengjunren.myblog.security.service.OnlineService;
import cn.zhengjunren.myblog.security.vo.OnlineUser;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>ClassName: OnlineController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 15:45
 */
@Api(tags = "在线用户管理")
@Slf4j
@RestController
@RequestMapping("/api/online")
public class OnlineController {

    private final OnlineService onlineService;

    public OnlineController(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @MyLog("查询在线用户")
    @GetMapping
    public ApiResponse page(OnlineQueryCondition condition){
        return ApiResponse.ofSuccess(onlineService.page(condition));
    }

    @MyLog("导出在线用户excel")
    @GetMapping("excel")
    @ApiOperation(value = "导出在线用户excel")
    public void exportExcel(HttpServletResponse response) throws IOException, ClassNotFoundException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=online.xlsx");
        EasyExcel.write(response.getOutputStream(), OnlineUser.class).sheet("sheet1").doWrite(onlineService.list());
    }

    @MyLog("踢出用户")
    @DeleteMapping
    @ApiOperation(value = "踢出用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true, dataType = DataTypeUtils.STRING, paramType = ParamTypeUtils.QUERY),
    })
    public ApiResponse kickOut(String key, HttpServletRequest request) throws Exception {
        onlineService.kickOut(key, request);
        return ApiResponse.ofSuccess();
    }
}
