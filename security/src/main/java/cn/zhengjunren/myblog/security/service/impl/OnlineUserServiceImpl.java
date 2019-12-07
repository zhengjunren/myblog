package cn.zhengjunren.myblog.security.service.impl;

import cn.zhengjunren.myblog.commons.dto.IpInfo;
import cn.zhengjunren.myblog.commons.utils.EncryptUtils;
import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import cn.zhengjunren.myblog.commons.domain.TbUser;
import cn.zhengjunren.myblog.security.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>ClassName: OnlineUserServiceImpl</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/7 9:49
 */
@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    private final RedisTemplate redisTemplate;

    @Value("${business.onlineKey}")
    private String onlineKey;

    @Value("${business.expiration}")
    private Long expiration;

    public OnlineUserServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void save(TbUser tbUser, String token,HttpServletRequest request) {
        String browser = UserAgentUtils.getBrowser(request).toString();
        String ipAddr = UserAgentUtils.getIpAddr(request);
        IpInfo ipInfo = UserAgentUtils.getIpInfo(ipAddr);
        OnlineUser onlineUser = new OnlineUser();
        try {
            onlineUser.setBrowser(browser);
            onlineUser.setIp(ipAddr);
            onlineUser.setAddress(String.format("%s|%s|%s|%s", ipInfo.getCountry(),ipInfo.getRegion(), ipInfo.getCity(), ipInfo.getIsp()));
            onlineUser.setUsername(tbUser.getUsername());
            onlineUser.setKey(EncryptUtils.desEncrypt(token));
            onlineUser.setLoginTime(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(onlineKey + token, onlineUser);
        redisTemplate.expire(onlineKey + token,expiration, TimeUnit.MILLISECONDS);
    }
}
