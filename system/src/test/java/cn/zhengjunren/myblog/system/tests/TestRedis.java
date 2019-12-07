package cn.zhengjunren.myblog.system.tests;

import cn.zhengjunren.myblog.commons.dto.IpInfo;
import cn.zhengjunren.myblog.commons.log.domain.TbLog;
import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import cn.zhengjunren.myblog.system.SystemApplication;
import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>ClassName: TestRedis</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/6 18:08
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class TestRedis {

    @Resource(name = "redisTemplate")
    RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        redisTemplate.delete("log");
    }


    @Test
    public void get() {
        // 测试线程安全，程序结束查看redis中count的值是否为1000
//        ExecutorService executorService = Executors.newFixedThreadPool(1000);
//        IntStream.range(0, 1000).forEach(i -> executorService.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));
//
//        stringRedisTemplate.opsForValue().set("k1", "v1");
//        String k1 = stringRedisTemplate.opsForValue().get("k1");
//        log.debug("【k1】= {}", k1);

        // 以下演示整合，具体Redis命令可以参考官方文档
        String key = "myblog:log";
        TbLog tbLog = new TbLog();
        tbLog.setBrowser("谷歌");
        tbLog.setAddress("江西");
//
//        TbLog tbLog1 = new TbLog();
//        tbLog1.setBrowser("火狐");
//        tbLog1.setAddress("南昌");
//        redisTemplate.expire()
//        redisTemplate
//                .opsForList().rightPush(key, tbLog);

//        redisTemplate.opsForList().rightPush(key, tbLog1);
        Long size = redisTemplate.opsForList().size(key);
//        List<TbLog> serializable = redisTemplate.opsForList().range(key,0,size == null ? 500 : size );
//        redisTemplate.opsForList().remove()
//        assert serializable != null;
//        for (TbLog log : serializable) {
//            System.out.println(log.toString());
//        }
//        redisTemplate.opsForList().g
        // 对应 String（字符串）
//        User user = (User) redisCacheTemplate.opsForValue().get(key);
//        log.debug("【user】= {}", user);
    }

    public void delete() {

        String key = "myblog:log";
        TbLog tbLog = new TbLog();
        tbLog.setBrowser("谷歌");
        tbLog.setAddress("江西");

        TbLog tbLog1 = new TbLog();
        tbLog1.setBrowser("火狐");
        tbLog1.setAddress("南昌");

    }

    @Test
    public void keys(){
        List<String> keys = new ArrayList<>(redisTemplate.keys("online-key*"));
        System.out.println(Arrays.toString(keys.toArray()));
        for (String key : keys) {
            OnlineUser o = (OnlineUser)redisTemplate.opsForValue().get(key);
            assert o != null;
            System.out.println(o.toString());
        }
    }

    @Test
    public void testIp() {
        String ip = "182.106.212.141";
        IpInfo ipInfo = UserAgentUtils.getIpInfo(ip);
        System.out.println(ipInfo.toString());
    }
}
