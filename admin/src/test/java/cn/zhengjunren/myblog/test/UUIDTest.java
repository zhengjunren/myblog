package cn.zhengjunren.myblog.test;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;

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
}
