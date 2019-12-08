package cn.zhengjunren.test;

import cn.zhengjunren.myblog.commons.utils.UserAgentUtils;
import org.junit.Test;

/**
 * <p>ClassName: TestIp2Region</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/8 19:39
 */

public class TestIp2Region {


    @Test
    public void test() throws Exception {
        System.out.println(UserAgentUtils.ip2Region("171.34.140.13"));
//        String path = "ip2region/ip2region.db";
//        String name = "ip2region.db";
//        DbConfig config = new DbConfig();
//        File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
//        DbSearcher searcher = new DbSearcher(config, file.getPath());
//        Method method;
//        method = searcher.getClass().getMethod("btreeSearch", String.class);
//        DataBlock dataBlock;
//        dataBlock = (DataBlock) method.invoke(searcher, "171.34.140.13");
//        String address = dataBlock.getRegion().replace("0|","");
//        if(address.charAt(address.length()-1) == '|'){
//            address = address.substring(0,address.length() - 1);
//        }
//        System.out.println(address.equals("内网IP|内网IP") ? "内网IP" : address);
    }

}
