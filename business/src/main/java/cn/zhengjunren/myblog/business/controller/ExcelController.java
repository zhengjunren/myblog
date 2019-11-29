package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.business.domain.TbUser;
import cn.zhengjunren.myblog.business.service.TbUserService;
import cn.zhengjunren.myblog.commons.log.annotation.MyLog;
import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.log.service.TbLogService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;

/**
 * <p>ClassName: ExcelController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/16 11:20
 */
@RequestMapping("excel")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@Api(tags = "excel导出")
public class ExcelController {

    private final TbUserService tbUserService;

    private final TbLogService tbLogService;

    public ExcelController(TbUserService tbUserService, TbLogService tbLogService) {
        this.tbUserService = tbUserService;
        this.tbLogService = tbLogService;
    }

    @MyLog("导出用户列表excel")
    @GetMapping("user")
    @ApiOperation(value = "导出用户列表excel")
    public void downloadUser(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TbUser.class).sheet("sheet1").doWrite(tbUserService.selectAll());
    }

    @MyLog("导出日志列表excel")
    @GetMapping("log")
    @ApiOperation(value = "导出日志列表excel")
    public void downloadLog(HttpServletResponse response, Timestamp start, Timestamp end) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("日志", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TbLog.class).sheet("sheet1").doWrite(tbLogService.selectAll(start, end));
    }
}
