package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.TbEmailConfig;
import cn.zhengjunren.myblog.system.dto.EmailForm;
import org.springframework.scheduling.annotation.Async;

public interface TbEmailConfigService{

    /**
     * 更新邮件配置
     * @param emailConfig 邮件配置
     * @param old 旧的配置
     * @return
     */
    int update(TbEmailConfig emailConfig, TbEmailConfig old);

    /**
     * 查询配置
     * @return EmailConfig 邮件配置
     */
    TbEmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    String send(EmailForm emailVo, TbEmailConfig emailConfig) throws Exception;
}
