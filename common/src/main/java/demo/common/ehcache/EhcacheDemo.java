package demo.common.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;

public class EhcacheDemo {
	
	@Test
	public void test1() {
		CacheManager manager = CacheManager.create();
		manager.addCache("c1");
		Cache cache = manager.getCache("c1");
		cache.put(new Element("1", "abc"));
		Element element = cache.get("1");
		System.out.println(element.getObjectValue());
	}
	
	@Test
	public void test2() {
		CacheManager manager = CacheManager.create();
		{
			Cache cache = manager.getCache("test");
			cache.put(new Element("1", "abc"));
			Element element = cache.get("1");
			System.out.println(element.getObjectValue());
			System.out.println(cache.remove("1"));
			System.out.println(cache.get("1"));
		}
		manager.removeCache("test");
//		Cache cache = manager.getCache("test");
//		System.out.println(cache.get("1").getObjectValue());
		CacheManager.getInstance().shutdown();
	}
	
	@Test
	public void test3() {
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("test");
		cache.put(new Element("1", "abc"));
		Element element = cache.get("1");
		System.out.println(element.getObjectValue());
		System.out.println(cache.remove("1"));
		System.out.println(cache.get("1"));
	}

	@Test
	public void test4() {
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache("test");
		cache.put(new Element("1", "abc"));
		System.out.println(cache.get("1"));
		cache.flush();
	}

	@Test
	public void test5() throws Exception {
		CacheManager manager = CacheManager.create();
		manager.getCache("test").get("1");
	}

}
