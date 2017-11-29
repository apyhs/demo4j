package demo.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import demo.jdk.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

public class MailKit {
	private static final String defaultMailPropName = "mail.properties";
	private static final String usernameKey = "mail.auth.username";
	private static final String passwordKey = "mail.auth.password";

	public static Session createSession() throws GeneralSecurityException, IOException {
		return createSession(defaultMailPropName);
	}

	public static Session createSession(String mailPropName) throws GeneralSecurityException, IOException {
		return createSession(PropKit.useless(mailPropName));
	}

	public static Session createSession(Properties prop) throws GeneralSecurityException {
		boolean smtpSsl = "true".equals(prop.getProperty("mail.smtp.ssl.enable"));
		boolean imapSsl = "true".equals(prop.getProperty("mail.imap.ssl.enable"));
		if (smtpSsl || imapSsl) {
			MailSSLSocketFactory factory = new MailSSLSocketFactory();
			factory.setTrustAllHosts(true);
			if (smtpSsl) prop.put("mail.smtp.ssl.socketFactory", factory);
			if (imapSsl) prop.put("mail.imap.ssl.socketFactory", factory);
		}
		return Session.getInstance(prop, null);
	}

	public static void setDebug(Session session, boolean debug) {
		session.setDebug(debug);
	}

	public static Store getStore(Session session) throws MessagingException {
		return getStore(session, null);
	}

	public static Store getStore(Session session, String protocol) throws MessagingException {
		String username = session.getProperty(usernameKey);
		String password = session.getProperty(passwordKey);
		Store store = StrKit.isBlank(protocol) ? session.getStore() : session.getStore(protocol);
		if (StrKit.isBlank(username) && StrKit.isBlank(password)) {
			store.connect();
		} else {
			store.connect(username, password);
		}
		return store;
	}

	public static Folder getFolder(Store store, String folderName) throws MessagingException {
		return getFolder(store, folderName, false);
	}

	public static Folder getFolder(Store store, String folderName, boolean isReadOnly) throws MessagingException {
		Folder folder = store.getFolder(folderName);
		folder.open(isReadOnly ? Folder.READ_ONLY : Folder.READ_WRITE);
		return folder;
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
		return StrKit.notBlank(pattern) ? TimeKit.format(date, pattern) : TimeKit.format(date);
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
					FileKit.create(dest);
					StreamKit.writeAndClose(in, new FileOutputStream(dest));
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart,destDir);
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), destDir);
		}
	}

	public static void send(Session session, MsgBuilder builder) throws MessagingException {
		MimeMessage message = builder.build();
		String username = session.getProperty(usernameKey);
		String password = session.getProperty(passwordKey);
		if (StrKit.isBlank(username) && StrKit.isBlank(password)) {
			Transport.send(message);
		} else {
			Transport transport = session.getTransport();
			transport.connect(username, password);
			Address[] addresses = message.getRecipients(Message.RecipientType.TO);
			transport.sendMessage(message, addresses);
			transport.close();
		}
	}

	public static void close(Folder... folders) throws MessagingException {
		if (folders != null && folders.length != 0) {
			for (Folder folder : folders) folder.close(true);
		}
	}

	public static void close(Store store, Folder... folders) throws MessagingException {
		close(folders);
		if (store != null) store.close();
	}

	public static StringBuilder statusSummary(Folder folder) throws MessagingException {
		StringBuilder builder = new StringBuilder();
		builder.append("Unread  : ").append(folder.getUnreadMessageCount()).append(StrKit.ENDL);
		builder.append("Deleted : ").append(folder.getDeletedMessageCount()).append(StrKit.ENDL);
		builder.append("New     : ").append(folder.getNewMessageCount()).append(StrKit.ENDL);
		builder.append("All     : ").append(folder.getMessageCount()).append(StrKit.ENDL);
		return builder;
	}

	public static StringBuilder mailSummary(Message... messages) throws IOException, MessagingException {
		StringBuilder builder = new StringBuilder();
		if(messages != null && messages.length != 0) {
			for (Message message : messages) {
				int messageNumber = message.getMessageNumber();
				builder.append("---------------- ").append(messageNumber).append(" ----------------").append(StrKit.ENDL);
				builder.append("Subject    : ").append(getSubject(message)).append(StrKit.ENDL);
				builder.append("From       : ").append(getFrom(message)).append(StrKit.ENDL);
				builder.append("Receiver   : ").append(getReceiver(message)).append(StrKit.ENDL);
				builder.append("SentDate   : ").append(TimeKit.format(getSentDate(message))).append(StrKit.ENDL);
				builder.append("isRead     : ").append(isRead(message)).append(StrKit.ENDL);
				builder.append("Priority   : ").append(getPriority(message)).append(StrKit.ENDL);
				builder.append("Size       : ").append(message.getSize()).append(" byte").append(StrKit.ENDL);
				builder.append("Attachment : ").append(containAttachment(message)).append(StrKit.ENDL);
				String content = getTextContent(message).trim();
				content = content.length() > 64 ? content.substring(0, 64) : content;
				builder.append("Content    : ").append(content).append(StrKit.ENDL);
				builder.append("---------------- ").append(messageNumber).append(" ----------------").append(StrKit.ENDL);
				builder.append(StrKit.ENDL);
			}
		}
		return builder;
	}

	public static StringBuilder mailDetail(Message message) throws IOException, MessagingException {
		StringBuilder builder = new StringBuilder();
		builder.append("Subject    : ").append(getSubject(message)).append(StrKit.ENDL);
		builder.append("From       : ").append(getFrom(message)).append(StrKit.ENDL);
		builder.append("Receiver   : ").append(getReceiver(message)).append(StrKit.ENDL);
		builder.append("SentDate   : ").append(getSentDate(message)).append(StrKit.ENDL);
		builder.append("isRead     : ").append(isRead(message)).append(StrKit.ENDL);
		builder.append("Priority   : ").append(getPriority(message)).append(StrKit.ENDL);
		builder.append("Size       : ").append(message.getSize()).append(" byte").append(StrKit.ENDL);
		builder.append("Attachment : ").append(containAttachment(message)).append(StrKit.ENDL);
		builder.append("Content    : ").append(getTextContent(message)).append(StrKit.ENDL);
		return builder;
	}

}
