package demo.demo.webmagic;

import org.junit.Test;
import us.codecraft.webmagic.Spider;

public class Demo {

    @Test
    public void test1() {
        Spider.create(new ProcessorDemo())
                .addUrl("http://www.sse.com.cn/assortment/stock/list/info/company/index.shtml?COMPANY_CODE=600000")
                .thread(5)
                .run();
    }

}
