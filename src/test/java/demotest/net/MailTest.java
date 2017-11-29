package demotest.net;

import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.Test;
import demo.base.HttpUtils;
import demo.base.TimeUtils;
import demo.net.Email;
import demo.net.EmailUtils;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import java.net.URL;

public class MailTest {
    private String user = "kahle@foxmail.com";
    private String password = "vtgmnyanrczibeih";
    private String to = "kahlkn@163.com";

    public static Email.Props createEmailProps() {
        return new Email.Props()
                .setSmtpHost("smtp.qq.com")
                .setSmtpPort(465)
                .setImapHost("imap.qq.com")
                .setImapPort(993)
                .setStoreProtocol("imap")
                .setSslOnConnect(true)
                .setDebug(true);
    }

    @Test
    public void defaultSendMail() throws Throwable {
        new Email()
            .setProps(createEmailProps())
            .setUser(user)
            .setPassword(password)
            .setFrom(user, "HELLO")
            .setTo(to)
            .setSentDate(TimeUtils.newDate(1995, 5, 5))
            .setSubject("测试")
            .setTextContent("世界，你好！")
            .setHtmlContent("<html><h1>Hello, World!</h1><img src=\"" +
                    "http://uux.me/wp-content/uploads/2016/12/2016121558520aa97be57.png\" /></html>")
            .send();
    }

    @Test
    public void defaultReceiveMail() throws Throwable {
        // JDK版本需要1.7，因为SSL加密问题
        Session session = EmailUtils.createSession(createEmailProps().build());
        Store store = EmailUtils.getStore(session, user, password);
        Folder inbox = EmailUtils.getFolder(store, "INBOX");
        System.out.println(EmailUtils.statusSummary(inbox));
        System.out.println(EmailUtils.emailSummary(inbox.getMessages()));
        EmailUtils.close(inbox);
        EmailUtils.close(store);
    }

    @Test
    public void apacheSendMail() throws Exception {
        SimpleEmail email = new SimpleEmail();
        email.setDebug(true);
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setCharset("utf-8");
        email.setSSLOnConnect(true).setStartTLSEnabled(true);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setFrom(user).addTo(to);
        email.setSubject("TestCommonMail");
        email.setMsg("This is a test mail ... :-)");
        email.send();
    }

    @Test
    public void apacheSendHtmlMail() throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setDebug(true);
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setCharset("utf-8");
        email.setSSLOnConnect(true).setStartTLSEnabled(true);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setFrom(user).addTo(to);
        email.setSubject("TestCommonMail");
        String cid = email.embed(new URL("http://uux.me/wp-content/uploads/2016/12/2016121558520aa97be57.png"), "Hello, World! ");
        email.setHtmlMsg("<html>Hello, World! - <img src='cid:"+cid+"'></html>");
        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
    }

    @Test
    public void apacheSendAttachMail() throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setDebug(true);
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setCharset("utf-8");
        email.setSSLOnConnect(true).setStartTLSEnabled(true);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setFrom(user).addTo(to);
        email.setSubject("TestCommonMail");
        email.setHtmlMsg("<h1>This is a test mail ... :-)</h1>");
        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");

        // 从本地添加附件
        EmailAttachment csv = new EmailAttachment();
        csv.setName(MimeUtility.encodeText("你好.csv"));
        csv.setPath("E:\\1.csv");
        csv.setDescription(EmailAttachment.ATTACHMENT);

        // 从URL添加附件
        EmailAttachment png = new EmailAttachment();
        png.setName(MimeUtility.encodeText("好看.png"));
        png.setURL(new URL("http://uux.me/wp-content/uploads/2016/12/2016121558520aa97be57.png"));
        png.setDisposition(EmailAttachment.ATTACHMENT);
        png.setDescription("维和梓喵");

        email.attach(csv);
        email.attach(png);
        email.send();
    }

    @Test
    public void apacheSendImageHtmlEmail() throws Exception {
        // html中的图片以内联图片的形式展示
        String htmlEmailTemplate = HttpUtils.get("http://uux.me/2");
        URL url = new URL("http://uux.me");
        ImageHtmlEmail email = new ImageHtmlEmail();
        email.setDebug(true);
        email.setHostName("smtp.qq.com");
        email.setSmtpPort(465);
        email.setCharset("utf-8");
        email.setSSLOnConnect(true).setStartTLSEnabled(true);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setFrom(user).addTo(to);
        email.setDataSourceResolver(new DataSourceUrlResolver(url));
        email.setSubject("Test email with inline image");
        // set the html message
        email.setHtmlMsg(htmlEmailTemplate);
        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");
        email.send();
    }

}
