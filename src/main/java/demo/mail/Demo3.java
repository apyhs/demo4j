package demo.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;

import org.junit.Test;

import kit4j.mail.MailKit;
import kit4j.mail.PropBuilder;
import kit4j.mail.Session;

public class Demo3 {
	
	String pwd = "pwd";
	String from = "1@foxmail.com";
	String to = "2@foxmail.com";
	String disName = "习大大";
	
//	@Test
//	public void t1(){
//		Properties prop = PropBuilder.create("smtp.qq.com", true).ssl().prop();
//		Session session = MailKit.session(prop, true);
//		MimeMessage message = MsgBuilder.create(session)
//			.setFrom(from, disName)
//			.setTo(to)
//			.setSubject("这是一封测试邮件")
//			.setHtmlContent("<h1>测试邮件！</h1><br />这是一份测试邮件，你好！", new File("D:\\测试文件.txt"))
//			.setSentDate(new Date())
//			.save();
//		MailKit.send(from, pwd, session, message);
//	}
	
	@Test
	public void t2(){
		Properties prop = PropBuilder.create().smtpHost("smtp.qq.com").imapHost("imap.qq.com").ssl().prop();
		Session session = Session.create(prop).auth(from, pwd).debug(true);
		session.send(session.msg().setFrom(from, disName).setTo(to)
			.setSubject("这是一封测试邮件")
			.setHtmlContent("<h1>测试邮件！</h1><br />这是一份测试邮件，你好！", new File("D:\\测试文件.txt"))
			.setSentDate(new Date()).save());
	}
	
	@Test
	public void t3(){
		Properties prop = PropBuilder.create().smtpHost("smtp.qq.com").imapHost("imap.qq.com").ssl().prop();
		Session session = Session.create(prop).auth(from, pwd).debug(false);
		POP3ReceiveMailTest.rec(session);
	}
	
	@Test
	public void t4() throws Exception{
		Properties prop = PropBuilder.create().smtpHost("smtp.qq.com").imapHost("imap.qq.com").ssl().prop();
		Session session = Session.create(prop).auth(from, pwd).debug(false);
		Store store = session.getStore();
		Folder folder = MailKit.getFolder(store, "inbox");
		System.out.println(MailKit.stateInfo(folder));
		System.out.println(MailKit.msgList(folder.getMessages()));
		MailKit.close(store, folder);
	}
	
	@Test
	public void t5() throws Exception{
		Properties prop = PropBuilder.create().smtpHost("smtp.qq.com").imapHost("imap.qq.com").ssl().prop();
		Session session = Session.create(prop).auth(from, pwd).debug(false);
		Store store = session.getStore();
		Folder folder = MailKit.getFolder(store, "inbox");
		Message message = folder.getMessage(folder.getMessageCount());
		System.out.println(MailKit.msgDetail(message));
		MailKit.saveAttachment(message, "c:\\");
		MailKit.close(store, folder);
	}
	
}
