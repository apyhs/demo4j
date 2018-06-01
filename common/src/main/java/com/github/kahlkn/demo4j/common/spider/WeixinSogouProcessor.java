package com.github.kahlkn.demo4j.common.spider;

import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class WeixinSogouProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

    private String aaa;

    private Integer step = 0;

    public String getAaa() {
        return aaa;
    }

    public WeixinSogouProcessor setAaa(String aaa) {
        this.aaa = aaa;
        return this;
    }

    @Override
    public void process(Page page) {
        if (step == 0) {
            List<String> list = page.getHtml().xpath("//*[@id=\"sogou_vr_11002301_box_0\"]")
                    .css("p.tit").css("a", "href").all();
            if (list == null || list.size() == 0) {
                System.out.println("找不到！！！！！！！");
                return;
            }
            System.out.println(list);
            page.addTargetRequest(list.get(0));
            step = 1;
        }
        else if (step == 1) {
            Selectable css = page.getHtml().css("div#WXAPPMSG1000000358");
            System.out.println("title : " + css.css("h4.weui_media_title").xpath("//*h4/text()").get());
            System.out.println("desc : " + css.css("p.weui_media_desc").xpath("//*p/text()").get());
            System.out.println("time : " + css.css("p.weui_media_extra_info").xpath("//*p/text()").get());
            String hrefs = css.css("h4.weui_media_title", "hrefs").get();
            System.out.println("hrefs : " + hrefs);
            page.addTargetRequest(hrefs);
            aaa = hrefs;
            page.putField("aaa", hrefs);
            step = 2;
        }
        else if (step == 2) {
            Selectable table = page.getHtml().xpath("//*[@id=\"js_content\"]/section").css("table");
            List<Selectable> trs = table.css("tr").nodes();
            if (trs != null && trs.size() != 0) {
                for (Selectable tr : trs) {
                    List<Selectable> tds = tr.css("td").nodes();
                    if (tds == null || tds.size() == 0) continue;
                    for (Selectable td : tds) {
                        List<Selectable> spans = td.css("span").nodes();
                        if (spans == null || spans.size() == 0) continue;
                        for (Selectable span : spans) {
                            System.out.print(span.xpath("//*span/text()").get() + " ");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Test
    public void main() {
        System.setProperty("selenuim_config", "E:\\123.md");
        String url = "http://weixin.sogou.com/weixin?type=1&s_from=input&query=%E4%B8%AD%E5%AE%87%E8%B5%84%E8%AE%AF&ie=utf8&_sug_=n&_sug_type_=";
        WeixinSogouProcessor processor = new WeixinSogouProcessor();
        Spider spider = Spider.create(processor).setDownloader(
                new SeleniumDownloader("D:\\Kit\\WebDriver\\chromedriver.exe")).addUrl(url).thread(5);
        spider.run();
        System.out.println(processor.getAaa());
        System.out.println();
    }

}
