package demo.net;

import demo.base.ArrayUtils;
import demo.base.CollectionUtils;
import demo.base.StrUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Email {
    private String charset = Charset.defaultCharset().name();
    private Session session;
    private Boolean debug;
    private String fromString;
    private Address fromAddress;
    private Address[] toAddress;
    private String toString;
    private Date sentDate;
    private String subject;
    private String textContent;
    private String htmlContent;
    private List<File> files;
    private Props props;
    private String user;
    private String password;

    public String getCharset() {
        return charset;
    }

    public Email setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public Session getSession() {
        return session;
    }

    public Email setSession(Session session) {
        this.session = session;
        return this;
    }

    public Boolean isDebug() {
        return debug;
    }

    public Email setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getFrom() {
        return StrUtils.isNotBlank(fromString) ?
                fromString : fromAddress != null ?
                fromAddress.toString() : null;
    }

    public Email setFrom(String froms) {
        this.fromString = froms;
        return this;
    }

    public Email setFrom(Address from) {
        this.fromAddress = from;
        return this;
    }

    public Email setFrom(String from, String displayName) {
        try {
            String encodeName = MimeUtility.encodeText(displayName);
            this.fromAddress = new InternetAddress(encodeName + " <" + from + ">");
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getTo() {
        return ArrayUtils.isNotEmpty(toAddress) ?
                InternetAddress.toString(toAddress) :
                StrUtils.isNotBlank(toString) ? toString : null;
    }

    public Email setTo(Address[] to) {
        this.toAddress = to;
        return this;
    }

    public Email setTo(String tos) {
        this.toString = tos;
        return this;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Email setSentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public Email setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Email setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
        return this;
    }

    public List<File> getFiles() {
        return files;
    }

    public Email setFiles(List<File> files) {
        this.files = files;
        return this;
    }

    public Email addFile(File file) {
        this.files.add(file);
        return this;
    }

    public Email addFiles(List<File> files) {
        this.files.addAll(files);
        return this;
    }

    public Props getProps() {
        return props;
    }

    public Email setProps(Props props) {
        this.props = props;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Email setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Email setPassword(String password) {
        this.password = password;
        return this;
    }

    public Email send() throws UnsupportedEncodingException, MessagingException {
        if (StrUtils.isNotBlank(user) && StrUtils.isNotBlank(password)) {
            Transport.send(this.build(), user, password);
        } else {
            Transport.send(this.build());
        }
        return this;
    }

    public MimeMessage build() throws MessagingException, UnsupportedEncodingException {
        if (session == null) {
            if (props != null) {
                session = Session.getDefaultInstance(props.build());
            } else {
                throw new NullPointerException("Session and Email Props both are null. ");
            }
        }
        if (debug != null) session.setDebug(debug);
        MimeMessage message = new MimeMessage(session);
        if (StrUtils.isNotBlank(fromString)) {
            message.setFrom(fromString);
        } else if (fromAddress != null) {
            message.setFrom(fromAddress);
        } else {
            throw new NullPointerException("From is null. ");
        }
        if (ArrayUtils.isNotEmpty(toAddress)) {
            message.setRecipients(Message.RecipientType.TO, toAddress);
        } else if (StrUtils.isNotBlank(toString)) {
            message.setRecipients(Message.RecipientType.TO, toString);
        } else {
            throw new NullPointerException("To is null. ");
        }
        if (sentDate != null) message.setSentDate(sentDate);
        if (StrUtils.isNotBlank(subject)) message.setSubject(subject, charset);
        if (StrUtils.isNotBlank(htmlContent) || CollectionUtils.isNotEmpty(files)) {
            MimeMultipart mimeMultipart = new MimeMultipart();
            if (StrUtils.isNotBlank(htmlContent)) {
                String contentType = String.format("text/html; charset=%s", charset.toLowerCase());
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(htmlContent, contentType);
                mimeMultipart.addBodyPart(mimeBodyPart);
            }
            if (CollectionUtils.isNotEmpty(files)) {
                for (File file : files) {
                    MimeBodyPart filePart = new MimeBodyPart();
                    filePart.setDataHandler(new DataHandler(new FileDataSource(file)));
                    filePart.setFileName(MimeUtility.encodeWord(file.getName(), charset, "B"));
                    mimeMultipart.addBodyPart(filePart);
                }
            }
            message.setContent(mimeMultipart);
        } else if (StrUtils.isNotBlank(textContent)) {
            message.setText(textContent, charset);
        }
        return message;
    }

    public static class Props {
        private String smtpHost;
        private Integer smtpPort;
        private String imapHost;
        private Integer imapPort;
        private String pop3Host;
        private Integer pop3Port;
        private String storeProtocol;
        private Boolean debug;
        private Boolean sslOnConnect;

        public String getSmtpHost() {
            return smtpHost;
        }

        public Props setSmtpHost(String smtpHost) {
            this.smtpHost = smtpHost;
            return this;
        }

        public Integer getSmtpPort() {
            return smtpPort;
        }

        public Props setSmtpPort(Integer smtpPort) {
            this.smtpPort = smtpPort;
            return this;
        }

        public String getImapHost() {
            return imapHost;
        }

        public Props setImapHost(String imapHost) {
            this.imapHost = imapHost;
            return this;
        }

        public Integer getImapPort() {
            return imapPort;
        }

        public Props setImapPort(Integer imapPort) {
            this.imapPort = imapPort;
            return this;
        }

        public String getPop3Host() {
            return pop3Host;
        }

        public Props setPop3Host(String pop3Host) {
            this.pop3Host = pop3Host;
            return this;
        }

        public Integer getPop3Port() {
            return pop3Port;
        }

        public Props setPop3Port(Integer pop3Port) {
            this.pop3Port = pop3Port;
            return this;
        }

        public String getStoreProtocol() {
            return storeProtocol;
        }

        public Props setStoreProtocol(String storeProtocol) {
            this.storeProtocol = storeProtocol;
            return this;
        }

        public Boolean isDebug() {
            return debug;
        }

        public Props setDebug(Boolean debug) {
            this.debug = debug;
            return this;
        }

        public Boolean isSslOnConnect() {
            return sslOnConnect;
        }

        public Props setSslOnConnect(Boolean sslOnConnect) {
            this.sslOnConnect = sslOnConnect;
            return this;
        }

        public Properties build() {
            Properties props = new Properties();
            props.putAll(System.getProperties());
            if (StrUtils.isNotBlank(smtpHost)) props.setProperty("mail.smtp.host", smtpHost);
            if (smtpPort != null) props.setProperty("mail.smtp.port", smtpPort + "");
            if (StrUtils.isNotBlank(imapHost)) props.setProperty("mail.imap.host", imapHost);
            if (imapPort != null) props.setProperty("mail.imap.port", imapPort + "");
            if (StrUtils.isNotBlank(pop3Host)) props.setProperty("mail.pop3.host", pop3Host);
            if (pop3Port != null) props.setProperty("mail.pop3.port", pop3Port + "");
            if (StrUtils.isNotBlank(storeProtocol)) props.setProperty("mail.store.protocol", storeProtocol);
            if (debug != null) props.setProperty("mail.debug", debug + "");
            if (sslOnConnect != null && sslOnConnect) {
                if (StrUtils.isNotBlank(smtpHost)) props.setProperty("mail.smtp.ssl.enable", "true");
                if (StrUtils.isNotBlank(imapHost)) props.setProperty("mail.imap.ssl.enable", "true");
            }
            return props;
        }

    }

}
