package cn.zhengjunren.myblog.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.zhengjunren.myblog.admin.domain.Log;
import cn.zhengjunren.myblog.admin.dto.log.ErrorLogDto;
import cn.zhengjunren.myblog.admin.dto.log.InfoLogDto;
import cn.zhengjunren.myblog.admin.service.LogService;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.controller.BaseController;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class LogController extends BaseController<Log, LogService> {

    public LogController(LogService service) {
        super(service);
    }

    @GetMapping("/{type}")
    public ApiResponse page(long page, long limit, Timestamp start, Timestamp end, @PathVariable("type") String type) {
        return ApiResponse.ofSuccess(service.page(page, limit, start, end, type));
    }

    @GetMapping("/error/{id}")
    public ApiResponse getError(@PathVariable long id) {
        return ApiResponse.ofSuccess(service.getErrorDetail(id));
    }

    @Override
    @GetMapping("/info/excel")
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
    public ApiResponse delete(@PathVariable String type) {
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        logQueryWrapper.eq(Log.COL_LOG_TYPE, type);
        service.remove(logQueryWrapper);
        return ApiResponse.ofSuccess();
    }
}
