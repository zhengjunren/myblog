package cn.zhengjunren.myblog.test;

import cn.zhengjunren.myblog.BlogAdminApplication;
import cn.zhengjunren.myblog.common.utils.RedisUtil;
import cn.zhengjunren.myblog.security.vo.OnlineUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p>ClassName: RedisTest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 15:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogAdminApplication.class ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {

    @Value("${jwt.online-key}")
    private String onlineKey;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        List<String> keys = redisUtil.scan(onlineKey + "*");
        keys.stream().map(key->{
            OnlineUser onlineUser = (OnlineUser) redisUtil.get(key);
            return onlineUser;
        }).forEach(System.out::println);
//        System.out.println(Arrays.toString(keys.toArray()));
    }
}
