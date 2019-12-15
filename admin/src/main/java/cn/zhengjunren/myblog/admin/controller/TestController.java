package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.User;
import cn.zhengjunren.myblog.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>ClassName: TestController</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/15 21:13
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/test/1")
    public List<User> test(){
        return userService.list();
    }
}
