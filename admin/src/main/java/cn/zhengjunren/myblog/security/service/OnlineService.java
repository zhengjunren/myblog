package cn.zhengjunren.myblog.security.service;

import cn.hutool.core.util.NumberUtil;
import cn.zhengjunren.myblog.common.consts.Consts;
import cn.zhengjunren.myblog.common.dto.ListInfo;
import cn.zhengjunren.myblog.common.exception.BadRequestException;
import cn.zhengjunren.myblog.common.utils.EncryptUtils;
import cn.zhengjunren.myblog.common.utils.PageUtil;
import cn.zhengjunren.myblog.common.utils.RedisUtil;
import cn.zhengjunren.myblog.common.utils.UserAgentUtil;
import cn.zhengjunren.myblog.security.dto.OnlineQueryCondition;
import cn.zhengjunren.myblog.security.utils.JwtUtil;
import cn.zhengjunren.myblog.security.vo.OnlineUser;
import cn.zhengjunren.myblog.security.vo.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: OnlineService</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/27 14:27
 */
@Service
@Slf4j
public class OnlineService {

    private final RedisUtil redisUtil;

    private final JwtUtil jwtUtil;

    @Value("${jwt.online-key}")
    private String onlineKey;

    private final RedisTemplate<String, Serializable> redisTemplate;

    public OnlineService(RedisUtil redisUtil, JwtUtil jwtUtil, RedisTemplate<String, Serializable> redisTemplate) {
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Async
    public void save(UserPrincipal userPrincipal, HttpServletRequest request, String token) {
        try {
            String ipAddr = UserAgentUtil.getIpAddr(request);
            String region = UserAgentUtil.ip2Region(ipAddr);
            String browser = UserAgentUtil.getBrowser(request).toString();
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setUsername(userPrincipal.getUsername());
            onlineUser.setNickname(userPrincipal.getNickname());
            onlineUser.setBrowser(browser);
            onlineUser.setIp(ipAddr);
            onlineUser.setAddress(region);
            onlineUser.setKey(EncryptUtils.desEncrypt(token));
            onlineUser.setLoginTime(new Date());
            redisUtil.set(onlineKey+":"+token, onlineUser, 10800L);
        } catch (Exception e) {
            log.error("报错");
        }
    }

    public List<OnlineUser> getAll(String filter){
        List<String> keys = redisUtil.scan(onlineKey + "*");
        Collections.reverse(keys);
        List<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = (OnlineUser) redisUtil.get(key);
            if(StringUtils.isNotBlank(filter)){
                if(onlineUser.toString().contains(filter)){
                    onlineUsers.add(onlineUser);
                }
            } else {
                onlineUsers.add(onlineUser);
            }
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }

    public List<OnlineUser> list(){
        List<String> keys = redisUtil.scan(onlineKey + "*");
        Collections.reverse(keys);
        List<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = (OnlineUser) redisUtil.get(key);
            onlineUsers.add(onlineUser);
        }
        onlineUsers.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUsers;
    }


    public ListInfo page(OnlineQueryCondition condition){
        String page = String.valueOf(condition.getPage());
        String size = String.valueOf(condition.getLimit());
        List<OnlineUser> onlineUsers = getAll(condition.getFilter());
        List list = PageUtil.toPage(NumberUtil.parseInt(page) - 1, NumberUtil.parseInt(size), onlineUsers);
        return new ListInfo(list, onlineUsers.size());
    }

    public void kickOut(String key,  HttpServletRequest request) throws Exception{
        String token = token = EncryptUtils.desDecrypt(key);
        String key1 = onlineKey + ":" + EncryptUtils.desDecrypt(key);
        String requestToken = jwtUtil.getJwtFromRequest(request);
        if (!requestToken.equals(token)){
            redisUtil.del(key1);
            redisUtil.del(Consts.REDIS_JWT_KEY_PREFIX + jwtUtil.getUsernameFromJWT(token));
        }
        else {
            throw new BadRequestException(400, "不能踢出自己");
        }
    }

    public void kickOutSelf(String token){
        redisUtil.del(onlineKey + ":" + token);
    }

}
