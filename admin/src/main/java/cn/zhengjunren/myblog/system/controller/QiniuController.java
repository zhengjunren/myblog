package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.system.domain.QiniuConfig;
import cn.zhengjunren.myblog.system.domain.QiniuContent;
import cn.zhengjunren.myblog.system.dto.condition.QiniuQueryCondition;
import cn.zhengjunren.myblog.system.service.QiNiuService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: QiniuController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/1 14:10
 */
@RestController
@RequestMapping("api/qiniu")
public class QiniuController {


    private final QiNiuService qiNiuService;

    public QiniuController(QiNiuService qiNiuService) {
        this.qiNiuService = qiNiuService;
    }

    @GetMapping("/config")
    public ApiResponse getConfig() {
        return ApiResponse.ofSuccess(qiNiuService.find());
    }

    @PutMapping("/config")
    public ApiResponse updateConfig(@RequestBody QiniuConfig qiniuConfig) {
        qiNiuService.updateConfig(qiniuConfig);
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("上传文件")
    @PostMapping
    public ApiResponse upload(@RequestParam MultipartFile file) throws Exception {
        QiniuContent qiniuContent = qiNiuService.upload(file,qiNiuService.find());
        Map<String,Object> map = new HashMap<>(3);
        map.put("id",qiniuContent.getId());
        map.put("errno",0);
        map.put("data",new String[]{qiniuContent.getUrl()});
        return ApiResponse.ofSuccess(map);
    }

    @ApiOperation("同步七牛云数据")
    @PostMapping(value = "/synchronize")
    public ApiResponse synchronize(){
        qiNiuService.synchronize(qiNiuService.find());
        return ApiResponse.ofSuccess();
    }

    @GetMapping
    public ApiResponse page(QiniuQueryCondition condition) {
        return ApiResponse.ofSuccess(qiNiuService.page(condition));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename=filesInfo.xlsx");
        List<QiniuContent> list = qiNiuService.selectAll();
        EasyExcel.write(response.getOutputStream(), QiniuContent.class).sheet("sheet1").doWrite(list);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") long id) {
        qiNiuService.delete(qiNiuService.selectById(id), qiNiuService.find());
        return ApiResponse.ofSuccess();
    }
}