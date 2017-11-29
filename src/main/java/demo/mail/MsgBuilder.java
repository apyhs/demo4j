package demo.mail;

import demo.jdk.util.Builder;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;

public class MsgBuilder implements Builder<MimeMessage> {
	private MimeMessage message;

	public MsgBuilder(Session session) {
		message = new MimeMessage(session);
	}

	public MsgBuilder setFrom(String from, String displayName) throws UnsupportedEncodingException, MessagingException {
		String encodeName = MimeUtility.encodeText(displayName);
		message.setFrom(new InternetAddress(encodeName + " <" + from + ">"));
		return this;
	}

	public MsgBuilder setFrom(String from) throws MessagingException {
		message.setFrom(from);
		return this;
	}

	public MsgBuilder setFrom(Address from) throws MessagingException {
		message.setFrom(from);
		return this;
	}

	public MsgBuilder setTo(String... to) throws MessagingException {
		int len = to.length;
		InternetAddress[] addresses = new InternetAddress[len];
		for (int i = 0; i < len; ++i) {
			addresses[i] = new InternetAddress(to[i]);
		}
		message.setRecipients(Message.RecipientType.TO, addresses);
		return this;
	}

	public MsgBuilder setTo(Address... addresses) throws MessagingException {
		message.setRecipients(Message.RecipientType.TO, addresses);
		return this;
	}

	public MsgBuilder setSubject(String subject) throws MessagingException {
		message.setSubject(subject);
		return this;
	}

	public MsgBuilder setTextContent(String content) throws MessagingException {
		message.setText(content);
		return this;
	}

	public MsgBuilder setTextContent(String charset, String content) throws MessagingException {
		message.setText(content, charset);
		return this;
	}

	public MsgBuilder setHtmlContent(String content, File... files) throws UnsupportedEncodingException, MessagingException {
		return setHtmlContent(Charset.defaultCharset().name(), content, files);
	}

	public MsgBuilder setHtmlContent(String charset, String content, File... files) throws MessagingException, UnsupportedEncodingException {
		String contentType = "text/html; charset=" + charset.toLowerCase();
		MimeMultipart mimeMultipart = new MimeMultipart();
		MimeBodyPart htmlContent = new MimeBodyPart();
		htmlContent.setContent(content, contentType);
		mimeMultipart.addBodyPart(htmlContent);
		if (files != null && files.length > 0) {
			for (File file : files) {
				MimeBodyPart filePart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(file);
				filePart.setDataHandler(new DataHandler(fds));
				String fileName = file.getName();
				fileName = MimeUtility.encodeWord(fileName, charset, "B");
				filePart.setFileName(fileName);
				mimeMultipart.addBodyPart(filePart);
			}
		}
		message.setContent(mimeMultipart);
		return this;
	}

	public MsgBuilder setSentDate(Date date) throws MessagingException {
		message.setSentDate(date);
		return this;
	}

	@Override
	public MimeMessage build() throws MessagingException {
		message.saveChanges();
		return message;
	}

}
