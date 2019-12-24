package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.system.dto.info.FileInfo;
import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * <p>ClassName: UploadController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 17:39
 */
@RestController
@RequestMapping("/api/upload")
@Api(tags = "上传文件")
public class UploadController {
    private static final String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAIdQL25lDzsU6l";
    private static final String ACCESS_KEY_SECRET = "CJUgCmnbziyYzOLub8KtlEwHRdWZiZ";
    private static final String BUCKET_NAME = "zhengjunrensite";
    /**
     * 文件上传
     *
     * @param multipartFile @{code MultipartFile}
     * @return {@link ApiResponse} 文件上传路径
     */
    @MyLog("上传文件")
    @PostMapping(value = "", headers = "Content-Type=multipart/form-data")
    public ApiResponse upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null;
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(multipartFile.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            return ApiResponse.ofSuccess(new FileInfo("http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName));
        } catch (IOException e) {
            return ApiResponse.of(400, "文件上传失败，请重试", null);
        } finally {
            client.shutdown();
        }

    }
}
