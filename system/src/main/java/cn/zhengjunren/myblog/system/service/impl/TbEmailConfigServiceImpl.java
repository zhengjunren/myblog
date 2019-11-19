package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.zhengjunren.myblog.system.domain.TbEmailConfig;
import cn.zhengjunren.myblog.system.mapper.TbEmailConfigMapper;
import cn.zhengjunren.myblog.system.service.TbEmailConfigService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
@Service
public class TbEmailConfigServiceImpl implements TbEmailConfigService{

    @Resource
    private TbEmailConfigMapper tbEmailConfigMapper;

    @Override
    public TbEmailConfig getByFrom(String from) {
        Example example = new Example(TbEmailConfig.class);
        example.createCriteria().andEqualTo("from", from);
        return tbEmailConfigMapper.selectOneByExample(example);
    }

    @Override
    public TbEmailConfig update(TbEmailConfig tbEmailConfig, TbEmailConfig old) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.yeah.net");
        account.setPort(25);
        account.setAuth(true);
        account.setFrom("hutool@yeah.net");
        account.setUser("hutool");
        account.setPass("q1w2e3");

        MailUtil.send(account, CollUtil.newArrayList("hutool@foxmail.com"), "测试", "邮件来自Hutool测试",false);
        return null;
    }
}
