package cn.zhengjunren.myblog.business.controller;

import cn.zhengjunren.myblog.commons.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>ClassName: TestController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/12 9:38
 */
@RestController
public class TestController {

    @GetMapping("test")
    public ResponseResult test() {
        return new ResponseResult(200,"test success");
    }
}
