package cn.zhengjunren.myblog.business.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;
import cn.hutool.system.SystemUtil;
import cn.zhengjunren.myblog.business.BusinessApplication;
import cn.zhengjunren.myblog.business.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * <p>ClassName: HuToolTests</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/1 21:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessApplication.class)
public class HuToolTests {

    @Test
    public void testSystemTool(){
        System.out.println(SystemUtil.getJvmSpecInfo());
        System.out.println(SystemUtil.getJvmInfo());
        System.out.println(SystemUtil.getJavaSpecInfo());
        System.out.println(SystemUtil.getJavaInfo());
        System.out.println(SystemUtil.getOsInfo());
        System.out.println(SystemUtil.getHostInfo());
        System.out.println(SystemUtil.getRuntimeInfo());
    }

    @Test
    public void testTokenizerUtil() {
        TokenizerEngine engine = TokenizerUtil.createEngine();

//解析文本
        String text = "这两个方法的区别在于返回值";
        Result result = engine.parse(text);
//输出：这 两个 方法 的 区别 在于 返回 值
        String resultStr = CollUtil.join((Iterator<Word>)result, " ");
    }

    @Test
    public void testReflectUtil() {
        Method[] methods = ReflectUtil.getMethods(UserController.class);
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
