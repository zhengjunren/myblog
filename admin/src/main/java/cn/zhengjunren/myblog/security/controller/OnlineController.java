package cn.zhengjunren.myblog.security.controller;

import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.security.dto.OnlineQueryCondition;
import cn.zhengjunren.myblog.security.service.OnlineService;
import cn.zhengjunren.myblog.security.vo.OnlineUser;
import com.alibaba.excel.EasyExcel;
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
@Slf4j
@RestController
@RequestMapping("/api/online")
public class OnlineController {

    private final OnlineService onlineService;

    public OnlineController(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @GetMapping
    public ApiResponse page(OnlineQueryCondition condition){
        return ApiResponse.ofSuccess(onlineService.page(condition));
    }

    @GetMapping("excel")
    public void exportExcel(HttpServletResponse response) throws IOException, ClassNotFoundException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=online.xlsx");
        EasyExcel.write(response.getOutputStream(), OnlineUser.class).sheet("sheet1").doWrite(onlineService.list());
    }

    @DeleteMapping
    public ApiResponse kickOut(String key, HttpServletRequest request) throws Exception {
        onlineService.kickOut(key, request);
        return null;
    }
}
