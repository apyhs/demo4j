package demo.common.rss;

import artoria.time.DateUtils;
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedOutput;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Demo1 {

    private static final String MIME_TYPE = "application/xml; charset=UTF-8";
    // Rome中RSS的可选标准
    // rss_0.90, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0, rss_2.0, atom_0.3
    private static final String RSS_TYPE = "rss_2.0";

    private List<SyndEntry> getEntries(List<News> news) throws ParseException {
        List<SyndEntry> entries = new ArrayList<>();
        SyndEntry entry;
        SyndContent description;

        for (News dto : news) {
            entry = new SyndEntryImpl();
            entry.setTitle(dto.getTitle());
            entry.setLink(dto.getLink());
            entry.setPublishedDate(DateUtils.create(dto.getTime()).getDate());

            description = new SyndContentImpl();
            description.setType("text/html");
            description.setValue(dto.getContent());

            entry.setDescription(description);
            entries.add(entry);
        }
        return entries;
    }

    public SyndFeed createFeed(List<News> news) throws ParseException {
        SyndFeed feed = new SyndFeedImpl();

        feed.setTitle("站点标题 ");
        feed.setLink("http://uux.me");
        feed.setDescription("站点描述，站点描述");

        feed.setEntries(getEntries(news));
        return feed;
    }

    @Test
    public void test1() throws Exception {
        List<News> news = new ArrayList<>();
        news.add(new News().setTitle("hello").setTime(DateUtils.format()).setLink("http://uux.me/1").setContent("你好，世界"));
        news.add(new News().setTitle("1234").setTime(DateUtils.format()).setLink("http://uux.me/2").setContent("啊啊啊啊啊"));
        news.add(new News().setTitle("顶顶顶").setTime(DateUtils.format()).setLink("http://uux.me/3").setContent("啦啦啦啦"));
        news.add(new News().setTitle("去去去去").setTime(DateUtils.format()).setLink("http://uux.me/4").setContent("不不不不"));
        news.add(new News().setTitle("呃呃呃额额").setTime(DateUtils.format()).setLink("http://uux.me/5").setContent("钱钱钱钱钱"));
        SyndFeed feed = createFeed(news);
        feed.setFeedType(RSS_TYPE);
        // response.setContentType(MIME_TYPE);

        SyndFeedOutput output = new SyndFeedOutput();
        System.out.println(output.outputString(feed));
    }

}
