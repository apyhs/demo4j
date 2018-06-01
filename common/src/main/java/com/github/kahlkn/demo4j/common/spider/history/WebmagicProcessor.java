package com.github.kahlkn.demo4j.common.spider.history;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class WebmagicProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");

    private static String driverPath = "D:\\Kit\\WebDriver\\chromedriver.exe";


    @Override
    public void process(Page page) {
        // 设置驱动路径
        System.setProperty("webdriver.chrome.driver", driverPath);

        WebDriver driver = new ChromeDriver();
        driver.get(page.getUrl().get());

        // 等待浏览器引擎加载完成
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 从浏览器引擎中获取数据
        WebElement webElement = driver.findElement(By.id("tableData_stockListCompany"));
//        WebElement webElement = driver.findElement(By.xpath("//div[@class='table-responsive sse_table_T05']"));
        String str = webElement.getAttribute("outerHTML");
        System.out.println(str);

        Html html = new Html(str);
        System.out.println(html.xpath("//tbody/tr").all());
        String companyCode = html.xpath("//tbody/tr[1]/td/text()").get();
        String stockCode = html.xpath("//tbody/tr[2]/td/text()").get().split("/")[0];
        String name = html.xpath("//tbody/tr[5]/td/text()").get().split("/")[0];
        String department = html.xpath("//tbody/tr[14]/td/text()").get().split("/")[0];

        System.out.println(companyCode);
        System.out.println(stockCode);
        System.out.println(name);
        System.out.println(department);

        // 关闭
//        driver.close();
        // 关闭 并且 结束进程
        driver.quit();
    }

    @Override
    public Site getSite() {
        return site;
    }

}
