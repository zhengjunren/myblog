package cn.zhengjunren.myblog.test;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * <p>ClassName: UUIDTest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/29 1:17
 */
public class UUIDTest {

    @Test
    public void test() {
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void IdUtilTest() {
        System.out.println(IdUtil.fastSimpleUUID());
    }

    @Test
    public void runtimeUtilTest() {
//        Console.log(1);
        System.out.println(RuntimeUtil.execForStr("ping www.zhengjunren.cn"));
    }

    @Test
    public void getTime() {
        LocalDateTime now = LocalDateTime.now();
        now = now.minus(30, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        Timestamp createTime = new Timestamp(date.getTime());
        System.out.println(createTime);
    }
}
