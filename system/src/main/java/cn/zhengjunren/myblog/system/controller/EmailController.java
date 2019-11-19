package cn.zhengjunren.myblog.system.controller;

import cn.zhengjunren.myblog.system.service.TbEmailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: EmailController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/19 22:59
 */
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    TbEmailConfigService tbEmailConfigService;


}
