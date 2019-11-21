package cn.zhengjunren.myblog.system.tests;

import cn.zhengjunren.myblog.system.SystemApplication;
import cn.zhengjunren.myblog.system.dto.EmailForm;
import cn.zhengjunren.myblog.system.service.TbEmailConfigService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>ClassName: TestSendEmail</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/21 10:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class TestSendEmail {

    @Autowired
    private TbEmailConfigService tbEmailConfigService;

    @Test
    public void send() throws Exception {
        EmailForm emailForm = new EmailForm();
        emailForm.setTos(Lists.newArrayList("zhengjr989@163.com"));

        emailForm.setContent("这是测试");
        emailForm.setSubject("主题");

        tbEmailConfigService.send(emailForm, tbEmailConfigService.find());
    }
}
