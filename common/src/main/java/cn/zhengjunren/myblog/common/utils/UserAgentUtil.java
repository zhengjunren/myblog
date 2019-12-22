package cn.zhengjunren.myblog.common.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Method;

/**
 * <p>ClassName: UserAgentUtils</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/22 22:33
 */
public class UserAgentUtil {
    /**
     * 获取用户代理
     *
     * @param request {@link HttpServletRequest}
     * @return {@link UserAgent}
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }
    /**
     * 获取用户浏览器
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Browser}
     */
    public static Browser getBrowser(HttpServletRequest request) {
        return getUserAgent(request).getBrowser();
    }
    /**
     * 获取用户的 IP 地址
     *
     * @param request {@link HttpServletRequest}
     * @return {@code String} 用户 IP 地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String ip2Region(String ip) throws Exception {
        String path = "ip2region/ip2region.db";
        String name = "ip2region.db";
        DbConfig config = new DbConfig();
        File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
        DbSearcher searcher = new DbSearcher(config, file.getPath());
        Method method;
        method = searcher.getClass().getMethod("btreeSearch", String.class);
        DataBlock dataBlock;
        dataBlock = (DataBlock) method.invoke(searcher, ip);
        String address = dataBlock.getRegion().replace("0|","");
        if(address.charAt(address.length()-1) == '|'){
            address = address.substring(0,address.length() - 1);
        }
        return "内网IP|内网IP".equals(address) ? "内网IP" : address;
    }
}
