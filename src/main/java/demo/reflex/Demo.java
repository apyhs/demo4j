package demo.reflex;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import demo.poi.excel.AA;
import demo.pojo.User;

public class Demo {
	
	@Test
	public void t1() throws Exception{
		Object o = 12d;
		System.out.println(o.getClass().getName());
		
		AA object = new AA();
		 // 拿到该类
        Class<?> clz = object.getClass();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = clz.getDeclaredFields();
        System.out.println(clz.getDeclaredField("a").getType().getName());
        for (Field field : fields) {
			System.out.print(field.getName() + " >>>> ");
			System.out.println(field.getType().getName());
		}
	}
	
	@Test
	public void test1() throws Exception{
		Map<String, Class<?>> map = new HashMap<>();
		map.put("kit4j.poi.Excel", Class.forName("kit4j.poi.Excel"));
		long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		Class<?> obj = map.get("kit4j.poi.Excel");
		long end = System.currentTimeMillis();
		System.out.println("Map<String, Class<?>> 方式获取Class对象：" + (end - start));
		
//		start = System.currentTimeMillis();
//		@SuppressWarnings("unused")
//		Method method = userClz.getMethod("setName", String.class);
//		end = System.currentTimeMillis();
//		System.out.println("getMethod() 方式获取Method对象：" + (end - start));
//		
//		start = System.currentTimeMillis();
//		Field field = userClz.getDeclaredField("name");
//		field.setAccessible(true);
//		end = System.currentTimeMillis();
//		System.out.println("getField() 方式获取Field对象：" + (end - start));
	}
	
	@Test
	public void test2() throws Exception{
		long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		Class<?> obj = Class.forName("kit4j.poi.Excel");
		long end = System.currentTimeMillis();
		System.out.println("Class.forName 方式获取Class对象：" + (end - start));
		
//		start = System.currentTimeMillis();
//		@SuppressWarnings("unused")
//		Method method = userClz.getMethod("setName", String.class);
//		end = System.currentTimeMillis();
//		System.out.println("getMethod() 方式获取Method对象：" + (end - start));
//		
//		start = System.currentTimeMillis();
//		Field field = userClz.getDeclaredField("name");
//		field.setAccessible(true);
//		end = System.currentTimeMillis();
//		System.out.println("getField() 方式获取Field对象：" + (end - start));
	}
	
	@Test
	public void lop1() throws Exception{
		long all = 0;
		for (int i = 0; i < 10000; i++) {
			long start = System.currentTimeMillis();
			@SuppressWarnings("unused")
			Class<?> obj = Class.forName("kit4j.poi.Excel");
			long end = System.currentTimeMillis();
			System.out.println("Class.forName 方式获取Class对象：" + (end - start));
			all += (end - start);
		}
		System.out.println(all);
	}
	
	@Test
	public void lop2() throws Exception{
		long all = 0;
		for (int i = 0; i < 100000; i++) {
			long start = System.currentTimeMillis();
			Class<?> obj = Class.forName("kit4j.poi.Excel");
			@SuppressWarnings("unused")
			Method method = obj.getMethod("read", String.class);
			long end = System.currentTimeMillis();
			System.out.println("本次耗时：" + (end - start));
			all += (end - start);
		}
		System.out.println(all);
	}
	
	@Test
	public void lop3() throws Exception{
		long all = 0;
		for (int i = 0; i < 1000000; i++) {
			long start = System.currentTimeMillis();
			Class<?> clz = Class.forName("demo.pojo.User");
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
