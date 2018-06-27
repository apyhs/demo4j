package demo.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class Demo1 {

	@Test
	public void test1(){
		//获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<SecurityManager> factory =
				new IniSecurityManagerFactory("classpath:shiro.ini");
		//得到SecurityManager实例并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.err.println(e.getMessage());
		}
		//断言用户已经登录
		Assert.assertEquals(true, subject.isAuthenticated());
		//退出
		subject.logout();
	}

	@Test
	public void test2(){
		//获取SecurityManager工厂，此处使用ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory =
				new IniSecurityManagerFactory("classpath:shiro-realm.ini");
		//得到SecurityManager实例并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.err.println(e.getMessage());
		}
		//断言用户已经登录
		Assert.assertEquals(true, subject.isAuthenticated());
		//退出
		subject.logout();
	}
}
