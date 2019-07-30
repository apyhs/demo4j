package demo.ua;

import artoria.exception.ExceptionUtils;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import cz.mallat.uasparser.MultithreadedUASparser;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import org.junit.Test;

import java.io.IOException;

public class UserAgentDemo {
    private static Logger log = LoggerFactory.getLogger(UserAgentDemo.class);
    private static UASparser uasParser;

    static {
        try {
            uasParser = new MultithreadedUASparser(OnlineUpdater.getVendoredInputStream());
            // java.lang.UnsupportedClassVersionError:
            // cz/mallat/uasparser/UASparser : Unsupported major.minor version 51.0
            // 用jdk1.6测试时会报以上错，需要jdk1.7以上版本支持
        }
        catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Test
    public void parser() throws IOException {
        // Baiduspider+(+http://www.baidu.com/search/spider.htm)
        // BaiDuSpider
        // Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)
        // Googlebot/2.1 (+http://www.googlebot.com/bot.html)
        // Chrome (AppleWebKit/537.1; Chrome50.0; Windows NT 6.3) AppleWebKit/537.36 (KHTML like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393
        // Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14
        //
        //
        //
        //
        // Mozilla/5.0 (Windows NT 6.0; rv:2.0) Gecko/20100101 Firefox/4.0 Opera 12.14
        // Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; es-es) AppleWebKit/531.22.7 (KHTML, like Gecko)

//        String userAgentStr = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36";

//        String userAgentStr = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
//        String userAgentStr = "Mozilla/5.0 (X11; Linux i686; rv:64.0) Gecko/20100101 Firefox/64.0";
//        String userAgentStr = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        String userAgentStr = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
        UserAgentInfo userAgentInfo = uasParser.parse(userAgentStr);
        System.out.println("操作系统家族：" + userAgentInfo.getOsFamily());
        System.out.println("操作系统详细名称：" + userAgentInfo.getOsName());
        System.out.println("浏览器名称和版本:" + userAgentInfo.getUaName());
        System.out.println("类型：" + userAgentInfo.getType());
        System.out.println("浏览器名称：" + userAgentInfo.getUaFamily());
        System.out.println("浏览器版本：" + userAgentInfo.getBrowserVersionInfo());
        System.out.println("设备类型：" + userAgentInfo.getDeviceType());
    }

}
