package demo.ua;

import eu.bitwalker.useragentutils.UserAgent;
import org.junit.Test;

public class UserAgentUtilsTest {

    @Test
    public void test1() {
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

//        String userAgentStr = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
//        String userAgentStr = "Mozilla/5.0 (X11; Linux i686; rv:64.0) Gecko/20100101 Firefox/64.0";
//        String userAgentStr = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
        String userAgentStr = "Mozilla/5.0 (Windows; U; Windows NT 6.1; rv:2.2) Gecko/20110201";
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        System.out.println("user agent" + userAgent);
    }

}
