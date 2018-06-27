package demo.common.mail.utils;

import artoria.net.HttpUtils;
import artoria.time.DateUtils;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.Test;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.net.URL;
import java.util.Map;

public class EmailUtilsTest {
    private String user = "123@foxmail.com";
    private String password = "password";
    private String to = "abc@163.com";
    private Props props = Props
            .create(user, password)
            .setSmtpHost("smtp.qq.com")
            .setSmtpPort(465)
            .setImapHost("imap.qq.com")
            .setImapPort(993)
            .setStoreProtocol("imap")
            .setSslOnConnect(true)
            .setDebug(true);

    @Test
    public void defaultSendMail() throws Throwable {
        demo.common.mail.utils.Email.create(props)
            .setFrom(user, "HELLO")
            .setTo(to)
            .setSentDate(DateUtils.create(1995, 5, 5).getDate())
            .setSubject("测试")
            .setHtmlContent("<html><h1>Hello, World!</h1><img src=\"" +
                    "http://uux.me/wp-content/uploads/2016/12/2016121558520aa97be57.png\" /></html>")
            .send();
    }

    @Test
    public void defaultReceiveGetFolders() throws Throwable {
        Session session =  demo.common.mail.utils.EmailUtils.createSession(props.build());
        Store store = demo.common.mail.utils.EmailUtils.getStore(session, user, password);
        Folder[] list = demo.common.mail.utils.EmailUtils.getAllFolders(store);
        for (Folder folder : list) {
            System.out.println(">>>>>>>>" + folder.getFullName() + " > " + folder.getName());
        }
        store.close();
    }

    @Test
    public void defaultReceiveMail() throws Throwable {
        // JDK1.8报错的话，自行搜索解决，是JDK问题
        Session session = demo.common.mail.utils.EmailUtils.createSession(props.build());
        Store store = demo.common.mail.utils.EmailUtils.getStore(session, user, password);
        Folder inbox = demo.common.mail.utils.EmailUtils.getInbox(store);

        Message[] messages = inbox.getMessages();
        Message message = messages[messages.length - 1];
         demo.common.mail.utils.Email parse = demo.common.mail.utils.Email.parse(message);
        System.out.println(parse.toString());

        if (parse.isHaveAttach()) {
            Map<String, File> files = parse.saveAttach(
                    new File("d:\\_cache")).getFiles();
            for (File file : files.values()) {
                System.out.println(file.toString());
            }
        }

        inbox.close(true);
        store.close();
    }

    @Test
    public void defaultReceiveGetAllMail() throws Throwable {
        // not test
        Session session = demo.common.mail.utils.EmailUtils.createSession(props.build());
        Store store = demo.common.mail.utils.EmailUtils.getStore(session, user, password);
        Folder inbox = demo.common.mail.utils.EmailUtils.getInbox(store);
        int count = inbox.getMessageCount();
        Message[] messages = inbox.getMessages(1, count);
        for (Message message : messages) {
            System.out.println(demo.common.mail.utils.Email.parse(message).toString());
        }
        inbox.close(true);
        store.close();
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
        String htmlEmailTemplate = HttpUtils.create("http://uux.me/2").get();
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
