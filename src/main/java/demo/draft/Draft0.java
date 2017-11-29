package demo.draft;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import demo.kit.TempKit;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import kit4j.FileKit;
import kit4j.StrKit;
import kit4j.StreamKit;
import kit4j.ThreadKit;
import kit4j.log.LogKit;

public class Draft0 {
	
	public static String LINESEPARATOR = System.getProperty("line.separator");
	
	// Server Address Name Port : 127.0.0.1 | 127.0.0.1 | 8080
	// Remote Address Host Port : 127.0.0.1 | 127.0.0.1 | 49652
	// RequestURL : http://127.0.0.1:8080/products | Method : GET
	// Parameters : vendor:[1],agc:[QMBT],region:[6]
	// ContentType : null | CharacterEncoding : UTF8
	// Cookies : null
	public static String getRequestInfo(HttpServletRequest request){
		StringBuffer strb = new StringBuffer();
		
		strb
		.append("Server Address Name Port : " + request.getLocalAddr()+" | "+request.getLocalName()+" | "+request.getLocalPort()).append(LINESEPARATOR)
		.append("Remote Address Host Port : " + request.getRemoteAddr()+" | "+request.getRemoteHost()+" | "+request.getRemotePort()).append(LINESEPARATOR)
		.append("RequestURL : "+request.getRequestURL().toString()).append(" | ").append("Method : " + request.getMethod()).append(LINESEPARATOR);
		
		strb.append("Parameters : ");
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<String> keys = parameterMap.keySet();
		for (String key : keys) {
			strb.append(key).append(":[");
			String[] values = parameterMap.get(key);
			for (String value : values) {
				strb.append(value).append(",");
			}
			strb.deleteCharAt(strb.length() - 1).append("],");
		}
		strb.deleteCharAt(strb.length() - 1).append(LINESEPARATOR);
		
		strb
		.append("ContentType : " + request.getContentType()).append(" | ").append("CharacterEncoding : " + request.getCharacterEncoding()).append(LINESEPARATOR)
		.append("Cookies : "+ Arrays.toString(request.getCookies())).append(LINESEPARATOR);
		
		return strb.toString();
	}
	
	@Test
	public void testJson(){
		String jsonString = "{\"iv\":\"88536343\",\"key\":\"423deadebf26a1726ce2a47862255197\"}";
		@SuppressWarnings("unchecked")
		Map<String, String> map = JSON.parseObject(jsonString, Map.class);
		System.out.println(map);
		System.out.println(map.get("iv"));
		System.out.println(map.get("key"));
	}
	
	@Test
	public void removeRepeat(){
		List<String> list = new ArrayList<>();
		Collections.addAll(list, "aa","bb","cc","dd","aa","bb");
		System.out.println(list);
		
		Set<String> set = new HashSet<>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		
		System.out.println(list);
	}
	
	@Test
	public void removeRepeatTest(){
		List<String> list = new ArrayList<>();
		Collections.addAll(list, "aa","bb","cc","dd","aa","bb");
		System.out.println(list);
		StrKit.removeRepeat(list);
		System.out.println(list);
	}
	
	@Test
	public void test(){
		System.out.println(new Date().getTime());
		System.out.println(System.currentTimeMillis());
		System.out.println(1478151177);
		System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
		System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date((1478151177000L))));
	}
	
	@Test
	public void test1(){
		Object aa= new Draft0();
		System.out.println(aa.toString());
	}
	
	@Test
	public void test2() throws ParseException{
		 String num = new String("123");//ref是Object对象的强引用      
	     //将一个软引用指向对象，此时Object对象有两个引用      
	     SoftReference<String> sf = new SoftReference<>(num);         
	     num = null;//去除对象的强引用
	     System.out.println(sf.get());
	     sf.clear();
	     System.gc();//gc只有在内存不足是才会回收软引
	}
	
	@Test
	public void test3(){
		TempKit.create("123");
		for (int i = 0; i < 11; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; ; i++) {
						TempKit.use("123").put("abc"+i, i);
						System.out.println(TempKit.use("123").get("abc"+i));
						if(i % 100000 == 0)
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
					}
				}
			}).start();
		}
		for (int i = 0; ; i++) {
			TempKit.use("123").put("abc"+i, i);
			System.out.println(TempKit.use("123").get("abc"+i));
			if(i % 100000 == 0)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}
	
	@Test
	public void test4(){
		Map<Object, String> map = new HashMap<>();
		map.put("123", null);
		map.put(null, "aaa");
		System.out.println(map.get(null));
		System.out.println(map.size());
		System.out.println(map.get("123"));
		System.out.println(map.remove("123"));
		System.out.println(map.size());
	}
	
	@Test
	public void test5(){
		TempKit.use().put("123", "11111111");
		System.out.println(TempKit.use().get("123"));
		TempKit.use().put("123", null);
		System.out.println(TempKit.use().get("123"));
	}
	
	@Test
	public void test6(){
		byte[] bytes = StreamKit.read(FileKit.read("cache/Hello.txt"));
		System.out.println(new String(bytes));
	}
	
	@Test
	public void test7(){
		LogKit.info("aaaa");
//		System.out.println(getMethodName());
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (StackTraceElement stackTraceElement : stackTrace) {
			System.out.print(stackTraceElement.getMethodName()+" >>>> ");
			System.out.print(stackTraceElement.getClassName()+" >>>> ");
			System.out.print(stackTraceElement.getFileName()+" >>>> ");
			System.out.println(stackTraceElement.getLineNumber());
		}
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
	}
	
	@Test
	public void test8(){
		System.out.println(getMethod());
		System.out.println(ThreadKit.currentMethodName());
		System.out.println(ThreadKit.currentClassName());
		System.out.println(ThreadKit.currentFileName());
		System.out.println(ThreadKit.currentLineNumber());
	}
	
	public static String getMethod(){
//		return Thread.currentThread().getStackTrace()[2].getMethodName();
		return ThreadKit.currentMethodName();
	}
}
