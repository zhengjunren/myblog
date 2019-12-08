package cn.zhengjunren.myblog.commons.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.zhengjunren.myblog.commons.dto.IpInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>ClassName: UserAgentUtils</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/11/18 13:46
 */
public class UserAgentUtils {
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

    /**
     * 通过 IP 获取地址 (淘宝接口)
     *
     * @param ip {@code String} 用户 IP 地址
     * @return {@code String} 用户地址
     */
    public static IpInfo getIpInfo(String ip) {
        if ("127.0.0.1".equals(ip)) {
            ip = "127.0.0.1";
        }
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
            HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
            htpcon.setRequestMethod("GET");
            htpcon.setDoOutput(true);
            htpcon.setDoInput(true);
            htpcon.setUseCaches(false);
            InputStream in = htpcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder temp = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return MapperUtils.json2pojoByTree(temp.toString(),"data", IpInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new IpInfo();
        }

    }
}
