package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.EmailConfig;
import cn.zhengjunren.myblog.system.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>ClassName: EmailService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2020/1/1 23:12
 */
public interface EmailService {

    /**
     * 更新邮件配置
     * @param emailConfig 配置
     */
    void updateConfig(EmailConfig emailConfig);

    /**
     * 查询配置
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception;
}
