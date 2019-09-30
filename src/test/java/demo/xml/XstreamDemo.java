package demo.xml;

import com.thoughtworks.xstream.XStream;
import org.junit.Test;

public class XstreamDemo {

    @Test
    public void test1() {
        XmlUser user = new XmlUser();
        user.setUsername("testUser11");
        user.setPassword("123456");
        user.setEmail("zhangsan@email.com");

        //创建解析XML对象
        XStream xStream = new XStream();
        //设置别名, 默认会输出全路径
        xStream.alias("user", XmlUser.class);
        //转为xml
        String xml = xStream.toXML(user);
        System.out.println(xml);

    }

    @Test
    public void test2() {
        XStream xStream = new XStream();
        xStream.alias("user", XmlUser.class);

        String xml = "<user>\n" +
                "  <username>testUser11</username>\n" +
                "  <password>123456</password>\n" +
                "  <email>zhangsan@email.com</email>\n" +
                "</user>";

        //转对象
        XmlUser user = (XmlUser)xStream.fromXML(xml);
        System.out.println(user.toString());
    }

}
