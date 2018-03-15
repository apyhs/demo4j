package com.github.kahlkn.demo.rss;

import com.github.kahlkn.artoria.net.HttpUtils;
import org.junit.Test;

public class RomeRssOldTest {

    @Test
    public void test1() throws Exception {
        RomeRssOld rss = RomeRssOld.on("测试站点", "http://uux.me/feed", "这是测试站点吗？")
                .setFeedType(RomeRssOld.RssType.RSS_2_0);
        rss.addEntry("你好，世界！", "你好，世界！ 的 描述， 的 描述！ 啊啊啊")
                .addEntry("啊啊啊啊啊", "你阿斯顿撒旦飒飒的 描述！ 啊啊啊")
                .addEntry("额呃呃呃", "你好，啊是的撒大苏打述！ 啊啊啊");
        System.out.println(rss.outputString());
    }

    @Test
    public void test2() throws Exception {
        String xml = HttpUtils.create("http://uux.me/feed").get();
        RomeRssOld rss = RomeRssOld.on(xml, "utf-8");
        // System.out.println(rss.outputString());
        System.out.println(rss.getTitle());
        System.out.println(rss.getLink());
        System.out.println(rss.getDescription());
        System.out.println(rss.getFeed().getAuthors());
    }


}
