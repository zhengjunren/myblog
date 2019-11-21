package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import cn.zhengjunren.myblog.system.domain.TbEmailConfig;
import cn.zhengjunren.myblog.system.dto.EmailForm;
import cn.zhengjunren.myblog.system.service.TbEmailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * @date 2019/11/21 10:34
 */
@RestController
@RequestMapping("email")
@CrossOrigin(origins = "*", maxAge = 3600L)
public class EmailController {

    @Autowired
    private TbEmailConfigService tbEmailConfigService;

    @GetMapping
    public ResponseResult<TbEmailConfig> get(){
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取邮件配置", tbEmailConfigService.find());
    }


    @PutMapping
    public ResponseResult<Void> emailConfig(@RequestBody TbEmailConfig emailConfig){
        tbEmailConfigService.update(emailConfig,tbEmailConfigService.find());
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功");
    }

    @PostMapping
    public ResponseResult<Void> send(@RequestBody EmailForm emailForm) throws Exception {
        String result = tbEmailConfigService.send(emailForm, tbEmailConfigService.find());
        if ("发送成功".equals(result)) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "发送成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "发送失败");
    }
}
