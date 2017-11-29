package demo.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

public class Demo1 {

    public static Session buildSession(String host, boolean debug, boolean auth) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.debug", debug);
            props.put("mail.smtp.auth", auth);
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(props, null);
            session.setDebug(debug);
            return session;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MimeMessage buildMessage(Session session, String from, String displayName, String title, String content, String contentType, File[] files, String... to) {
        try {
            if (to != null && to.length > 0) {
                MimeMessage msg = new MimeMessage(session);
                if (displayName != null) {
                    String nick = "";
                    try {
                        nick = MimeUtility.encodeText(displayName);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    msg.setFrom(new InternetAddress(nick + " <" + from + ">"));
                } else {
                    msg.setFrom(new InternetAddress(from));
                }
                InternetAddress[] address = new InternetAddress[to.length];
                for (int i = 0; i < to.length; i++) {
                    address[i] = new InternetAddress(to[i]);
                }
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(title);
                msg.setSentDate(new Date());
                MimeMultipart htmlMultipart = new MimeMultipart();
                MimeBodyPart htmlContent = new MimeBodyPart();
                htmlContent.setContent(content, contentType);
                // htmlContent.setHeader("Content-Transfer-Encoding", "Base64");
                htmlMultipart.addBodyPart(htmlContent);
                if (files != null) {
                    for (File file : files) {
                        MimeBodyPart mdp = new MimeBodyPart();
                        // 得到文件数据源
                        FileDataSource fds = new FileDataSource(file);
                        // 得到附件本身并至入BodyPart
                        mdp.setDataHandler(new DataHandler(fds));
                        // 得到文件名同样至入BodyPart
                        mdp.setFileName(MimeUtility.encodeWord(file.getName(), "utf-8", "B"));
                        htmlMultipart.addBodyPart(mdp);
                    }
                }
                msg.setContent(htmlMultipart);
                msg.saveChanges();
                return msg;
            } else {
                throw new IllegalStateException("miss required params");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMail(boolean auth, Session session, String username, String password, MimeMessage msg) {
        try {
            if (auth) {
                Transport transport = session.getTransport();
                transport.connect(username, password);
                transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
                transport.close();
            } else {
                Transport.send(msg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
