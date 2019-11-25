package cn.zhengjunren.myblog.business.test;

import cn.zhengjunren.myblog.business.BusinessApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.AntPathMatcher;

/**
 * <p>ClassName: AntMatchTest</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/23 22:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessApplication.class)
public class AntMatchTest {

    @Test
    public void test(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/webjars/**", "/webjars/123");
        System.out.println(match);
    }
}
