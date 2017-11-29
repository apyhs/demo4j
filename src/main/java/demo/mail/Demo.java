package demo.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import com.sun.mail.util.MailSSLSocketFactory;

import kit4j.mail.MsgBuilder;

public class Demo {

	@Test
	public void sendEmail() {
		// 收件人电子邮箱
		String to = "123@abc.com";
		// 发件人电子邮箱
		String from = "lalala@abc.com";
		// 指定发送邮件的主机为 localhost
		String host = "localhost";
		// 获取系统属性
		Properties properties = System.getProperties();
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties);
		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: 头部头字段
			message.setSubject("This is the Subject Line!");
			// 设置消息体
			message.setText("This is actual message");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	@Test
	public void sendMailByQQ() {
		// 收件人电子邮箱
		String to = "skernel@foxmail.com";
		// 发件人电子邮箱
		String from = "kahle@foxmail.com";
		// 指定发送邮件的主机为 smtp.qq.com
		// String host = "smtp.qq.com"; // QQ 邮件服务器
		String host = "smtp.qq.com"; // QQ 邮件服务器
		// 获取系统属性
		Properties properties = System.getProperties();
		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("123@foxmail.com", "safgfaf"); // 发件人邮件用户名、密码
			}
		});
		session.setDebug(true);
		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);
			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from));
			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: 头部头字段
			message.setSubject("This is the Subject Line!");
			// 设置消息体
			message.setText("This is actual message");
			// 发送消息
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	@Test
	public void sendMail1() throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");// 必须 普通客户端
		// props.setProperty("mail.transport.protocol", "smtp");// 必须选择协议
		
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);// 设置debug模式 在控制台看到交互信息
		Message msg = new MimeMessage(session); // 建立一个要发送的信息
		msg.setText("just test");// 设置简单的发送内容
		msg.setFrom(new InternetAddress("fdas@foxmail.com"));// 发件人邮箱号
		msg.setSubject("just test");
		Transport transport = session.getTransport();// 发送信息的工具
		transport.connect("smtp.qq.com", 465, "fdas@foxmail.com", "asfsafsafsafafs");// 发件人邮箱号//
																						// 和密码
		// transport.connect("smtp.exmail.qq.com", 25,
		// "wenjian_332401890@qq.com", "MEIY0Umima");// 发件人邮箱号
		transport.sendMessage(msg, new Address[] { new InternetAddress("asfsa@foxmail.com")});// 对方的地址
		transport.close();
	}

	String pwd = "pwd";
	String from = "from@foxmail.com";
	String to = "to@foxmail.com";
	String disName = "习大大";
	
	@Test
	public void t1(){
		Session session = Demo1.buildSession("smtp.qq.com", true, true);
		MimeMessage message = Demo1.buildMessage(session, from, disName, "这是一封测试邮件", "<h1>测试邮件！</h1>", "text/html;charset=utf-8", null, to);
		Demo1.sendMail(true, session, from, pwd, message);
	}
	
	@Test
	public void t2(){
		Session session = Demo1.buildSession("smtp.qq.com", true, true);
		MimeMessage message = MsgBuilder.create(session)
			.setFrom(from, disName)
			.setTo(to)
			.setSubject("这是一封测试邮件")
//			.setHtmlContent("utf-8", "<h1>测试邮件！</h1><br />这是一份测试邮件，你好！", new File("D:\\测试文件.txt"))
//			.setHtmlContent("<h1>测试邮件！</h1><br />这是一份测试邮件，你好！", new File("D:\\测试文件.txt"))
			.setHtmlContent("<h1>测试邮件！</h1><br />这是一份测试邮件，你好！<br />这是一份测试邮件，你好！")
			.setSentDate(new Date()).save();
		Demo1.sendMail(true, session, from, pwd, message);
	}
	
	@Test
	public void t3(){
		Session session = Demo1.buildSession("smtp.qq.com", true, true);
		MimeMessage message = MsgBuilder.create(session)
			.setFrom(from, disName)
			.setTo(to)
			.setSubject("这是一封测试邮件")
//			.setTextContent("gbk", "你好，世界！")
			.setTextContent("你好，世界！")
			.setSentDate(new Date()).save();
		Demo1.sendMail(true, session, from, pwd, message);
	}
}
