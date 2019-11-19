package cn.zhengjunren.myblog.system.service;

import cn.zhengjunren.myblog.system.domain.TbEmailConfig;

public interface TbEmailConfigService{

    /**
     * 通过发件人获取配置信息
     * @param from 发件人邮箱
     * @return {@link TbEmailConfig}
     */
    TbEmailConfig getByFrom(String from);

    /**
     * 更新邮件配置
     * @param tbEmailConfig 邮件配置
     * @param old 旧的配置
     * @return {@link TbEmailConfig}
     */
    TbEmailConfig update(TbEmailConfig tbEmailConfig, TbEmailConfig old);
}
