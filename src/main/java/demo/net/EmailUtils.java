package demo.net;

import demo.base.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Date;
import java.util.Properties;

public class EmailUtils {

    public static Session getSession(Properties props) {
        return Session.getDefaultInstance(props, null);
    }

    public static Session getSession(Properties props, Authenticator authenticator) {
        return Session.getDefaultInstance(props, authenticator);
    }

    public static Session createSession(Properties props) {
        return Session.getInstance(props, null);
    }

    public static Session createSession(Properties props, Authenticator authenticator) {
        return Session.getInstance(props, authenticator);
    }

    public static void send(Message msg) throws MessagingException {
        Transport.send(msg);
    }

    public static void send(Message msg, Address[] addresses) throws MessagingException {
        Transport.send(msg, addresses);
    }

    public static void send(Message msg, String user, String password) throws MessagingException {
        Transport.send(msg, user, password);
    }

    public static void send(Message msg, Address[] addresses, String user, String password) throws MessagingException {
        Transport.send(msg, addresses, user, password);
    }

    public static Store getStore(Session session, String user, String password) throws MessagingException {
        return getStore(session, user, password, null);
    }

    public static Store getStore(Session session, String user, String password, String protocol) throws MessagingException {
        Store store = StrUtils.isBlank(protocol) ? session.getStore() : session.getStore(protocol);
        if (StrUtils.isBlank(user) && StrUtils.isBlank(password)) {
            store.connect();
        } else {
            store.connect(user, password);
        }
        return store;
    }

    public static Folder getFolder(Store store, String folderName) throws MessagingException {
        return getFolder(store, folderName, true);
    }

    public static Folder getFolder(Store store, String folderName, boolean isReadOnly) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(isReadOnly ? Folder.READ_ONLY : Folder.READ_WRITE);
        return folder;
    }

    public static void close(Folder folder) {
        if (folder != null) {
            try {
                folder.close(true);
            } catch (MessagingException e) {
                LogUtils.fine(e.getMessage(), e);
            }
        }
    }

    public static void close(Store store) {
        if (store != null) {
            try {
                store.close();
            } catch (MessagingException e) {
                LogUtils.fine(e.getMessage(), e);
            }
        }
    }

    public static boolean isRead(Message message) throws MessagingException {
        return message.getFlags().contains(Flags.Flag.SEEN);
    }

    public static String getPriority(Message message) throws MessagingException {
        String[] headers = message.getHeader("X-Priority");
        if (headers == null) return "Not Found";
        String header = headers[0];
        if (header.contains("1") || header.contains("High")) return "High";
        else if (header.contains("5") || header.contains("Low")) return "Low";
        else return "Normal";
    }

    public static String getFrom(Message message) throws MessagingException, UnsupportedEncodingException {
        Address[] froms = message.getFrom();
        if (froms == null || froms.length == 0) {
            throw new MessagingException("Don't have from!");
        }
        InternetAddress address = (InternetAddress) froms[0];
        String nickname = address.getPersonal();
        nickname = nickname != null ? MimeUtility.decodeText(nickname) : "";
        return nickname + "<" + address.getAddress() + ">";
    }

    public static String getReceiver(Message message) throws MessagingException {
        return getReceiver(message, null);
    }

    public static String getReceiver(Message message, Message.RecipientType type) throws MessagingException {
        StringBuilder result = new StringBuilder();
        Address[] addresses = type == null ? message.getAllRecipients() : message.getRecipients(type);
        if (addresses == null || addresses.length == 0) {
            throw new MessagingException("Don't have receiver!");
        }
        for (Address address : addresses) {
            result.append(((InternetAddress)address).toUnicodeString()).append(",");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static Date getSentDate(Message message) throws MessagingException {
        return message.getSentDate();
    }

    public static String getSentDate(Message message, String pattern) throws MessagingException {
        Date date = message.getSentDate();
        if (date == null) return "";
        return StrUtils.isNotBlank(pattern) ? TimeUtils.format(date, pattern) : TimeUtils.format(date);
    }

    public static String getSubject(Message message) throws UnsupportedEncodingException, MessagingException {
        return MimeUtility.decodeText(message.getSubject());
    }

    public static String getTextContent(Part part) throws MessagingException, IOException {
        StringBuilder builder = new StringBuilder();
        getTextContent(part, builder);
        return builder.toString();
    }

    public static void getTextContent(Part part, StringBuilder builder) throws MessagingException, IOException {
        boolean containTextAttach = part.getContentType().contains("name");
        if (part.isMimeType("text/*") && !containTextAttach) {
            builder.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getTextContent((Part)part.getContent(), builder);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getTextContent(bodyPart, builder);
            }
        }
    }

    public static boolean containAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT)
                        || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = containAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("application")) flag = true;
                    if (contentType.contains("name")) flag = true;
                }
                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = containAttachment((Part) part.getContent());
        }
        return flag;
    }

    public static void saveAttachment(Part part, String destDir) throws MessagingException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) ||
                        disp.equalsIgnoreCase(Part.INLINE) ||
                        bodyPart.getContentType().contains("name") ||
                        bodyPart.getContentType().contains("application"))) {
                    InputStream in = bodyPart.getInputStream();
                    File dest = new File(destDir, MimeUtility.decodeText(bodyPart.getFileName()));
                    FileUtils.create(dest);
                    StreamUtils.writeAndClose(in, new FileOutputStream(dest));
                } else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart,destDir);
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent(), destDir);
        }
    }

    public static StringBuilder statusSummary(Folder folder) throws MessagingException {
        StringBuilder builder = new StringBuilder();
        builder.append("Unread  : ").append(folder.getUnreadMessageCount()).append(StrUtils.ENDL);
        builder.append("Deleted : ").append(folder.getDeletedMessageCount()).append(StrUtils.ENDL);
        builder.append("New     : ").append(folder.getNewMessageCount()).append(StrUtils.ENDL);
        builder.append("All     : ").append(folder.getMessageCount()).append(StrUtils.ENDL);
        return builder;
    }

    public static StringBuilder emailSummary(Message... messages) throws IOException, MessagingException {
        StringBuilder builder = new StringBuilder();
        if(messages != null && messages.length != 0) {
            for (Message message : messages) {
                int messageNumber = message.getMessageNumber();
                builder.append("---------------- ").append(messageNumber).append(" ----------------").append(StrUtils.ENDL);
                builder.append("Subject    : ").append(getSubject(message)).append(StrUtils.ENDL);
                builder.append("From       : ").append(getFrom(message)).append(StrUtils.ENDL);
                builder.append("Receiver   : ").append(getReceiver(message)).append(StrUtils.ENDL);
                builder.append("SentDate   : ").append(TimeUtils.format(getSentDate(message))).append(StrUtils.ENDL);
                builder.append("isRead     : ").append(isRead(message)).append(StrUtils.ENDL);
                builder.append("Priority   : ").append(getPriority(message)).append(StrUtils.ENDL);
                builder.append("Size       : ").append(message.getSize()).append(" byte").append(StrUtils.ENDL);
                builder.append("Attachment : ").append(containAttachment(message)).append(StrUtils.ENDL);
                String content = getTextContent(message).trim();
                content = content.length() > 64 ? content.substring(0, 64) : content;
                builder.append("Content    : ").append(content).append(StrUtils.ENDL);
                builder.append("---------------- ").append(messageNumber).append(" ----------------").append(StrUtils.ENDL);
                builder.append(StrUtils.ENDL);
            }
        }
        return builder;
    }

    public static StringBuilder emailDetail(Message message) throws IOException, MessagingException {
        StringBuilder builder = new StringBuilder();
        builder.append("Subject    : ").append(getSubject(message)).append(StrUtils.ENDL);
        builder.append("From       : ").append(getFrom(message)).append(StrUtils.ENDL);
        builder.append("Receiver   : ").append(getReceiver(message)).append(StrUtils.ENDL);
        builder.append("SentDate   : ").append(getSentDate(message)).append(StrUtils.ENDL);
        builder.append("IsRead     : ").append(isRead(message)).append(StrUtils.ENDL);
        builder.append("Priority   : ").append(getPriority(message)).append(StrUtils.ENDL);
        builder.append("Size       : ").append(message.getSize()).append(" byte").append(StrUtils.ENDL);
        builder.append("Attachment : ").append(containAttachment(message)).append(StrUtils.ENDL);
        builder.append("Content    : ").append(getTextContent(message)).append(StrUtils.ENDL);
        return builder;
    }

}
