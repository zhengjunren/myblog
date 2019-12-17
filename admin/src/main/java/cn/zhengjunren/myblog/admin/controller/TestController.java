package cn.zhengjunren.myblog.admin.controller;

import cn.zhengjunren.myblog.admin.domain.User;
import cn.zhengjunren.myblog.admin.service.UserService;
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

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 测试访问权限放行
     * @return 哈哈哈
     */
    @GetMapping("/test/1")
    public List<User> test(){
        return userService.list();
    }
}
