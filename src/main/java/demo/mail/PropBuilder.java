package demo.mail;

import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;

public class PropBuilder {
	private Properties prop;
	private PropBuilder(){ }
	
	public static PropBuilder create() {
		PropBuilder builder = new PropBuilder();
		builder.prop = new Properties();
		return builder;
	}
	
	public static PropBuilder create(Properties prop) {
		PropBuilder builder = new PropBuilder();
		builder.prop = prop; return builder;
	}
	
	public PropBuilder smtpHost(String host) {
		prop.put("mail.smtp.host", host);
		return this;
	}
	
	public PropBuilder smtpPort(int port) {
		prop.put("mail.smtp.port", port);
		return this;
	}
	
	public PropBuilder imapHost(String host) {
		prop.put("mail.imap.host", host);
		return this;
	}
	
	public PropBuilder imapPort(int port) {
		prop.put("mail.imap.port", port);
		return this;
	}
	
	public PropBuilder pop3Host(String host) {
		prop.put("mail.pop3.host", host);
		return this;
	}
	
	public PropBuilder pop3Port(int port) {
		prop.put("mail.pop3.port", port);
		return this;
	}
	
	public PropBuilder ssl() {
		try {
			MailSSLSocketFactory factory = new MailSSLSocketFactory();
			factory.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", true);
			prop.put("mail.smtp.ssl.socketFactory", factory);
			prop.put("mail.imap.ssl.enable", true);
			prop.put("mail.imap.ssl.socketFactory", factory);
			return this;
		} catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public Properties prop() {
		return prop;
	}
}
