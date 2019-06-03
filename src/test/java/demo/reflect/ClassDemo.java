package demo.reflect;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.text.ParseException;

public class ClassDemo {

	@Test
	public void test2() throws ParseException {
		String num = new String("123");//ref是Object对象的强引用
		//将一个软引用指向对象，此时Object对象有两个引用
		SoftReference<String> sf = new SoftReference<>(num);
		num = null;//去除对象的强引用
		System.out.println(sf.get());
		sf.clear();
		System.gc();//gc只有在内存不足是才会回收软引
	}

	@Test
	public void lop3() throws Exception{
		long all = 0;
		for (int i = 0; i < 1000000; i++) {
			long start = System.currentTimeMillis();
			Class<?> clz = Class.forName("com.apyhs.demo.reflect.User");
			Object obj = clz.newInstance();
			Method method = clz.getMethod("setName", String.class);
			@SuppressWarnings("unused")
			Object invoke = method.invoke(obj, "你好！");
			long end = System.currentTimeMillis();
			System.out.println("本次耗时：" + (end - start));
			all += (end - start);
		}
		System.out.println(all);
	}

	@Test
	public void lop4() throws Exception{
		long all = 0;
		for (int i = 0; i < 1000000; i++) {
			long start = System.currentTimeMillis();
			User user = new User();
			user.setName("你好！");
			long end = System.currentTimeMillis();
			System.out.println("本次耗时：" + (end - start));
			all += (end - start);
		}
		System.out.println(all);
	}

}
