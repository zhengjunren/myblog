package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.common.annotation.MyLog;
import cn.zhengjunren.myblog.common.result.ApiResponse;
import cn.zhengjunren.myblog.common.utils.ParamTypeUtils;
import cn.zhengjunren.myblog.system.domain.EmailConfig;
import cn.zhengjunren.myblog.system.service.EmailService;
import cn.zhengjunren.myblog.system.vo.EmailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "邮件管理")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @MyLog("更新邮件配置")
    @PutMapping("/config")
    @ApiOperation(value = "更新邮件配置")
    @ApiImplicitParam(name = "emailConfig", value = "邮件配置", required = true, dataType = "EmailConfig", paramType = ParamTypeUtils.BODY)
    public ApiResponse updateConfig(@RequestBody EmailConfig emailConfig) {
        emailService.updateConfig(emailConfig);
        return ApiResponse.ofSuccess();
    }

    @MyLog("获取邮件配置")
    @GetMapping("/config")
    @ApiOperation(value = "获取邮件配置")
    public ApiResponse getConfig() {
        return ApiResponse.ofSuccess(emailService.find());
    }

    @MyLog("发送邮件")
    @PostMapping
    @ApiOperation("发送邮件")
    @ApiImplicitParam(name = "EmailVo", value = "邮件内容、接受者及主题", required = true, dataType = "EmailVo", paramType = ParamTypeUtils.BODY)
    public ApiResponse send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        emailService.send(emailVo,emailService.find());
        return ApiResponse.ofSuccess();
    }
}
