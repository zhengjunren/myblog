package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.utils.EncryptUtils;
import cn.zhengjunren.myblog.system.domain.EmailConfig;
import cn.zhengjunren.myblog.system.mapper.EmailConfigMapper;
import cn.zhengjunren.myblog.system.service.EmailService;
import cn.zhengjunren.myblog.system.vo.EmailVo;
import org.springframework.stereotype.Service;

/**
 * <p>ClassName: EmailServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/1 23:12
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailConfigMapper emailConfigMapper;

    public EmailServiceImpl(EmailConfigMapper emailConfigMapper) {
        this.emailConfigMapper = emailConfigMapper;
    }


    @Override
    public void updateConfig(EmailConfig emailConfig) {
        emailConfigMapper.updateById(emailConfig);
    }

    @Override
    public EmailConfig find() {
        return emailConfigMapper.selectById(1L);
    }

    @Override
    public void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception {
        if(emailConfig == null){
            throw new BadRequestException(500, "请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(500, e.getMessage());
        }
        account.setFrom(emailConfig.getUser()+"<"+emailConfig.getFromUser()+">");
        // ssl方式发送
        account.setSslEnable(true);
        String content = emailVo.getContent();
        // 发送
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        }catch (Exception e){
            throw new BadRequestException(500, e.getMessage());
        }
    }
}
