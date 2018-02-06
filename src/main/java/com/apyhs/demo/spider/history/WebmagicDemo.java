package com.apyhs.demo.spider.history;

import org.junit.Test;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class WebmagicDemo {

    @Test
    public void test1() {
        Spider.create(new WebmagicProcessor())
                .addUrl("https://www.zhihu.com/question/22918070")
                .thread(5)
                .run();
    }

    @Test
    public void test2() {
        Spider.create(new HuabanProcessor()).thread(5)
                .addUrl("https://www.zhihu.com/question/22918070")
                .addPipeline(new FilePipeline("/test"))
                .setDownloader(new SeleniumDownloader("D:\\Kit\\WebDriver\\chromedriver.exe"))
                .run();
    }

}
