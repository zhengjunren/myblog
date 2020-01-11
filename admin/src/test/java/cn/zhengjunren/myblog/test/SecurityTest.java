package cn.zhengjunren.myblog.test;

import cn.hutool.crypto.SecureUtil;
import org.junit.Test;

/**
 * <p>ClassName: SecurityTest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/11 19:23
 */
public class SecurityTest {

    @Test
    public void securityTest() {
        System.out.println(SecureUtil.sha256("1234"));
        System.out.println(SecureUtil.sha256());
    }
}
