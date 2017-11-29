package demo.mail;

import demo.StrKit;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Session {
	private javax.mail.Session session;
	private String username;
	private String password;
	
	public static Session create(Properties prop) {
		Session thisSession = new Session();
		thisSession.session = javax.mail.Session.getInstance(prop, null);
		return thisSession;
	}
	
	public javax.mail.Session convert() {
		return session;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Session debug(boolean debug) {
		session.setDebug(debug);
		return this;
	}
	
	public Session auth(String username, String password) {
		this.username = username;
		this.password = password;
		return this;
	}
	
	public MsgBuilder msg() {
		return MsgBuilder.create(session);
	}
	
	public Session send(MimeMessage message) {
		try {
			if(StrKit.isBlank(username) || StrKit.isBlank(password)) {
				Transport.send(message);
			} else {
				Transport transport = session.getTransport();
	            transport.connect(username, password);
	            Address[] addresses = message.getRecipients(Message.RecipientType.TO);
	            transport.sendMessage(message, addresses);
	            transport.close();
			} return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Store getStore() {
		try {
			Properties prop = session.getProperties();
			if (prop.containsKey("mail.imap.host"))
				return getStore("imap");
			else return getStore("pop3");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Store getStore(String protocol) {
		try {
			Store store = session.getStore(protocol);
			store.connect(username, password);
			return store;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
