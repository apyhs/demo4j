package demo.demo.rss;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.io.WireFeedInput;
import com.sun.syndication.io.WireFeedOutput;
import com.sun.syndication.io.XmlReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class Demo2 {

    @Test
    public void test1() throws Exception {
        // 新建Channel对象，对应rss中的<channel></channel>

        /* Channel对象有两个构造器，一个默认的无参构造器用于clone对象，
        * 平时创建Channel对象时只能使用有参构造器Channel(String type)
        * 这个参数type很讲究，起初我随便填写了一些字符串，都抛出异常，非法的type
        * 后来逼急了，上网把rome源码搞下来，才搞明白type得是"rss_x.x"这样的
        * rome的文档里也没有写明，浪费了不少时间研究这个type究竟应该是什么。
        */
        Channel channel = new Channel("rss_2.0");
        channel.setTitle("Test RSS channel's title");
        channel.setDescription("channel的描述");
        channel.setLink("http://hi.baidu.com/openj/rss");
        channel.setEncoding("utf-8");

        //这个list对应rss中的item列表
        List<Item> items = new ArrayList<>();
        //新建Item对象，对应rss中的<item></item>
        Item item = new Item();
        item.setAuthor("item author jnduan");
        item.setTitle("item title");

        //新建一个Description，它是Item的描述部分
        Description description = new Description();
        description.setType("item description type");
        description.setValue("item description value");
        item.setDescription(description);

        items.add(item);
        channel.setItems(items);

        //用WireFeedOutput对象输出rss文本
        WireFeedOutput out = new WireFeedOutput();
        String outputString = out.outputString(channel);
        // System.out.println(outputString);

        // 解析xml到rss
        WireFeedInput input = new WireFeedInput();
        XmlReader xmlReader = new XmlReader(new ByteArrayInputStream(outputString.getBytes()));
        WireFeed feed = input.build(xmlReader);
        System.out.println(feed.getClass());

        WireFeedOutput out1 = new WireFeedOutput();
        String outputString1 = out1.outputString(feed);
        System.out.println(outputString1);
    }

}
