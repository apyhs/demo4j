package demo.mail;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MsgBuilder {
	private MimeMessage message;
	private MsgBuilder(){ } 
	
	public static MsgBuilder create(Session session){
		MsgBuilder builder = new MsgBuilder();
		builder.message = new MimeMessage(session);
		return builder;
	}
	
	public MsgBuilder setFrom(String from, String displayName){
		try {
			String encodeName = MimeUtility.encodeText(displayName);
			message.setFrom(new InternetAddress(encodeName + " <" + from + ">"));
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setFrom(String from){
		try {
			message.setFrom(from);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setFrom(Address from){
		try {
			message.setFrom(from);;
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setTo(String... to){
		try { 
			int len = to.length;
			InternetAddress[] addresses = new InternetAddress[len];
	        for (int i = 0; i < len; ++i) {
	        	addresses[i] = new InternetAddress(to[i]);
	        } message.setRecipients(Message.RecipientType.TO, addresses);
	        return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setTo(InternetAddress... addresses){
		try {
			message.setRecipients(Message.RecipientType.TO, addresses);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * subject or title
	 */
	public MsgBuilder setSubject(String subject){
		try {
			message.setSubject(subject);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setTextContent(String content){
		try {
			message.setText(content);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setTextContent(String charset, String content){
		try {
			message.setText(content, charset);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setHtmlContent(String content, File... files){
		return setHtmlContent(Charset.defaultCharset().name(), content, files);
	}
	
	public MsgBuilder setHtmlContent(String charset, String content, File... files){
		try {
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
	        } message.setContent(mimeMultipart);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MsgBuilder setSentDate(Date date){
		try {
			message.setSentDate(date);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MimeMessage save(){
		try {
			message.saveChanges();
			return message;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
