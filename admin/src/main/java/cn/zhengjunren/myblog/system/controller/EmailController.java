package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.system.domain.EmailConfig;
import cn.zhengjunren.myblog.system.service.EmailService;
import cn.zhengjunren.myblog.system.vo.EmailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: EmailController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/1 23:14
 */
@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PutMapping("/config")
    public ApiResponse updateConfig(@RequestBody EmailConfig emailConfig) {
        emailService.updateConfig(emailConfig);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/config")
    public ApiResponse getConfig() {
        return ApiResponse.ofSuccess(emailService.find());
    }

    @PostMapping
    @ApiOperation("发送邮件")
    public ApiResponse send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        emailService.send(emailVo,emailService.find());
        return ApiResponse.ofSuccess();
    }
}
