package cn.zhengjunren.myblog.system.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.zhengjunren.myblog.log.domain.Log;
import cn.zhengjunren.myblog.log.dto.ErrorLogDto;
import cn.zhengjunren.myblog.log.dto.InfoLogDto;
import cn.zhengjunren.myblog.log.service.LogService;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.utils.DataTypeUtils;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>ClassName: LogController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/23 11:36
 */
@RestController
@RequestMapping("/api/logs")
@Api(tags = "日志管理")
public class LogController extends BaseController<Log, LogService> {

    public LogController(LogService service) {
        super(service);
    }

    @GetMapping("/{type}")
    @ApiOperation(value = "查询日志", notes="根据时间、类型、分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "limit", value = "笔数", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "start", value = "开始", required = true, dataType = DataTypeUtils.DATETIME, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "end", value = "截止", required = true, dataType = DataTypeUtils.DATETIME, paramType = ParamTypeUtils.QUERY),
            @ApiImplicitParam(name = "type", value = "类型：为 INFO 或 ERROR", required = true, dataType = DataTypeUtils.STRING, paramType = ParamTypeUtils.PATH),
    })
    public ApiResponse page(long page, long limit, Timestamp start, Timestamp end, @PathVariable("type") String type) {
        return ApiResponse.ofSuccess(service.page(page, limit, start, end, type));
    }

    @GetMapping("/error/{id}")
    @ApiOperation(value = "查询异常", notes="根据id查询异常")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = DataTypeUtils.LONG, paramType = ParamTypeUtils.PATH)
    public ApiResponse getError(@PathVariable long id) {
        return ApiResponse.ofSuccess(service.getErrorDetail(id));
    }

    @Override
    @GetMapping("/info/excel")
    @ApiOperation(value = "导出info日志excel")
    public void exportExcel(HttpServletResponse response) throws IOException, ClassNotFoundException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=infoLog.xlsx");
        List<InfoLogDto> infoLogList = service.getInfoLogList();
        EasyExcel.write(response.getOutputStream(), InfoLogDto.class).sheet("sheet1").doWrite(infoLogList);
    }

    @GetMapping("/error/excel")
    @ApiOperation(value = "导出error日志excel")
    public void exportErrorLogExcel(HttpServletResponse response) throws IOException, ClassNotFoundException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=errorLog.xlsx");
        List<ErrorLogDto> collect = service.list(new QueryWrapper<Log>().eq(Log.COL_LOG_TYPE, Consts.ERROR_LOG_TYPE)).stream().map(log -> {
            ErrorLogDto errorLogDto = new ErrorLogDto();
            BeanUtils.copyProperties(log, errorLogDto);
            byte[] exceptionDetail = log.getExceptionDetail();
            errorLogDto.setExceptionDetail(new String(ObjectUtil.isNotNull(exceptionDetail) ? exceptionDetail : "".getBytes()));
            return errorLogDto;
        }).collect(Collectors.toList());
        EasyExcel.write(response.getOutputStream(), ErrorLogDto.class).sheet("sheet1").doWrite(collect);
    }

    @DeleteMapping("/{type}")
    @ApiOperation(value = "清空日志")
    @ApiImplicitParam(name = "type", value = "类型：为 INFO 或 ERROR", required = true, dataType = DataTypeUtils.STRING, paramType = ParamTypeUtils.PATH)
    public ApiResponse delete(@PathVariable String type) {
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        logQueryWrapper.eq(Log.COL_LOG_TYPE, type);
        service.remove(logQueryWrapper);
        return ApiResponse.ofSuccess();
    }
}
