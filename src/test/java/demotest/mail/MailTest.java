package demotest.mail;

import org.junit.Test;
import demo.jdk.TimeKit;
import demo.mail.MailKit;
import demo.mail.MsgBuilder;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

public class MailTest {

    @Test
    public void test1() throws Exception {
        Session session = MailKit.createSession();
        MsgBuilder builder = new MsgBuilder(session)
                .setFrom("zhangsan@foxmail.com", "HELLO")
                .setTo("lisi@foxmail.com")
                .setSentDate(TimeKit.newDate(1995, 5, 5))
                .setSubject("测试")
                .setTextContent("世界，你好！");
        MailKit.send(session, builder);
    }

    @Test
    public void test2() throws Exception {
        // JDK版本需要1.7，因为SSL加密问题
        Session session = MailKit.createSession();
        Store store = MailKit.getStore(session);
        Folder inbox = MailKit.getFolder(store, "INBOX");
        System.out.println(MailKit.statusSummary(inbox));
        System.out.println(MailKit.mailSummary(inbox.getMessages()));
    }

}
