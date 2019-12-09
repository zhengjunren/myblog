package cn.zhengjunren.myblog.system.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import cn.zhengjunren.myblog.commons.utils.EncryptUtils;
import cn.zhengjunren.myblog.system.domain.TbEmailConfig;
import cn.zhengjunren.myblog.system.dto.EmailForm;
import cn.zhengjunren.myblog.system.mapper.TbEmailConfigMapper;
import cn.zhengjunren.myblog.system.service.TbEmailConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author ZhengJunren
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbEmailConfigServiceImpl implements TbEmailConfigService{

    @Resource
    private TbEmailConfigMapper tbEmailConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(TbEmailConfig emailConfig, TbEmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbEmailConfigMapper.updateByPrimaryKey(emailConfig);
    }

    @Override
    public TbEmailConfig find() {
        Optional<TbEmailConfig> emailConfig = Optional.ofNullable(tbEmailConfigMapper.selectByPrimaryKey(1));
        return emailConfig.orElseGet(TbEmailConfig::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String send(EmailForm emailVo, TbEmailConfig emailConfig) throws Exception {
        if(emailConfig == null){
            throw new Exception("请先配置，再操作");
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
            throw new Exception(e.getMessage());
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
            return "发送成功";
        }catch (Exception e){
            return "发送失败";
        }
    }
}
