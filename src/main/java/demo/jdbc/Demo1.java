package demo.jdbc;

import java.util.List;

import org.junit.Test;


public class Demo1 {
	
	@Test
	public void test1(){
		DbConf conf = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root", "root"); conf.init(); DbKit.init(conf.getDataSource());
		
		User user = DbKit.queryBean(User.class, "select * from tb_user");
		System.out.println(user);
	}
	
	@Test
	public void test2(){
		DbConf conf = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root", "root"); conf.init(); DbKit.init(conf.getDataSource());
		
		List<User> list = DbKit.queryListBean(User.class, "select * from tb_user");
		for (User user : list) System.out.println(user);
	}
	
	@Test
	public void test3(){
		DbConf conf = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root", "root"); conf.init(); DbKit.init(conf.getDataSource());
				
		System.out.println(DbKit.update("insert into tb_user(user_name, password, name) values(?, ?, ?)", "张三", "123", "zhangsan"));
		System.out.println(DbKit.queryArray("SELECT LAST_INSERT_ID();")[0]);
		List<User> list = DbKit.queryListBean(User.class, "select * from tb_user");
		for (User user : list) System.out.println(user);
	}
	
	@Test
	public void test4(){
		DbConf conf = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root", "root"); conf.init(); DbKit.init(conf.getDataSource());
				
		DbKit.update("CREATE TABLE `tb_role` (`id` bigint(20) NOT NULL AUTO_INCREMENT, `name` varchar(32) NOT NULL, `pid` bigint(20) NOT NULL, PRIMARY KEY (`id`));");
	}
}
