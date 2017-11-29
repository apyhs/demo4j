package demo.kit;

import java.lang.reflect.Field;

import demo.pojo.User;
import kit4j.ClassKit;
import org.junit.Test;

import kit4j.ClassKit;

public class ClassKitTest {
	
	@Test
	public void set() throws ReflectiveOperationException{
		User user = new User();
		ClassKit.setter(user, "name", "张三");
		ClassKit.setter(user, "pass", "123456");
		System.out.println(ClassKit.getter(user, "name"));
		System.out.println(ClassKit.getter(user, "gender"));
		System.out.println(user);
	}
	
	@Test
	public void get() throws ReflectiveOperationException{
		User user = new User();
		ClassKit.setter(user, "name", "张三");
		ClassKit.setter(user, "pass", "123456");
		System.out.println(ClassKit.getter(user, "name"));
		System.out.println(ClassKit.getter(user, "gender"));
		System.out.println(user);
	}

	@Test
	public void invoke() throws ReflectiveOperationException{
		User user = new User();
		ClassKit.invoke(user, "setName", false, "张三");
		ClassKit.invoke(user, "setPass", false, "123456");
		System.out.println(ClassKit.invoke(user, "getName", false));
		System.out.println(ClassKit.invoke(user, "getGender", false));
		System.out.println(user);
	}
	
	@Test
	public void invoke0() throws ReflectiveOperationException{
		Object user = ClassKit.newInstance(User.class);
		ClassKit.invoke(user, "setName", false, "张三");
		ClassKit.invoke(user, "setPass", false, "123456");
		ClassKit.invoke(user, "setGender", true, "女");
		System.out.println(ClassKit.invoke(user, "getName", false));
		System.out.println(ClassKit.invoke(user, "getPass", true));
		System.out.println(ClassKit.invoke(user,"getGender", false));
		System.out.println(ClassKit.invoke(user, "toString", false));
	}
	
	@Test
	public void test1() throws ReflectiveOperationException{
		Field field = User.class.getDeclaredField("hello");
		field.setAccessible(true);
		System.out.println(field.getName());
		System.out.println(field.get(field));
		field.set(field, "不好！");
		System.out.println(field.get(field));
	}
	
	@Test
	public void test2() throws ReflectiveOperationException{
		System.out.println(ClassKit.get(User.class, "hello", true));
		ClassKit.set(User.class, "hello", true, "李四");
		System.out.println(ClassKit.get(User.class, "hello", true));
	}
	
	@Test
	public void test3(){
		Object user = ClassKit.newInstance(User.class, true, "男");
		System.out.println(ClassKit.invoke(user, "toString", false));
		Object user1 = ClassKit.newInstance(User.class, true);
		System.out.println(ClassKit.invoke(user1, "toString", false));
	}
}
