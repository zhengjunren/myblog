package cn.zhengjunren.myblog.system.service.impl;

import cn.zhengjunren.myblog.commons.domain.OnlineUser;
import cn.zhengjunren.myblog.commons.utils.EncryptUtils;
import cn.zhengjunren.myblog.commons.utils.OkHttpClientUtil;
import cn.zhengjunren.myblog.commons.utils.PageUtil;
import cn.zhengjunren.myblog.system.service.OnlineUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Value("${business.securityUrl}")
    private String securityUrl;

    public OnlineUserServiceImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public List<OnlineUser> selectAll(String filter) {
        List<String> keys = new ArrayList<>(redisTemplate.keys(onlineKey + "*"));
        Collections.reverse(keys);
        List<OnlineUser> onlineUsers = new ArrayList<>();
        for (String key : keys) {
            OnlineUser onlineUser = (OnlineUser)redisTemplate.opsForValue().get(key);
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

//    @Override
//    public List<OnlineUser> page(String filter, Integer pageNum, Integer pageSize) {
//        List<OnlineUser> onlineUsers = selectAll(filter);
//        List list = PageUtil.toPage(pageNum, pageSize, onlineUsers);
//    }

    @Override
    public Page<OnlineUser> page(String filter, Pageable pageable){
        List<OnlineUser> onlineUsers = selectAll(filter);

        return new PageImpl<OnlineUser>(
                PageUtil.toPage(pageable.getPageNumber(),pageable.getPageSize(),onlineUsers),
                pageable,
                onlineUsers.size());
    }
    @Override
    public void kickOut(String val) throws Exception {
        String key = onlineKey + EncryptUtils.desDecrypt(val);
        redisTemplate.delete(key);
        String uri = "/user/logout?access_token=" + EncryptUtils.desDecrypt(val);
        OkHttpClientUtil.getInstance().postData(securityUrl + uri, null);
    }
}
