package demo.ehcache;

import demo.log.LogKit;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;

import java.io.InputStream;
import java.net.URL;

public class CacheKit {
	
	private static CacheManager cacheManager = null;
	private static volatile Object locker = new Object();
	
	/**
	 * thread safety
	 */
	public static void init() {
		if(cacheManager == null)
			cacheManager = CacheManager.create();
	}
	
	/**
	 * thread safety
	 */
	public static void init(String configurationFilePath) {
		if(cacheManager == null)
			cacheManager = CacheManager.create(configurationFilePath);
	}
	
	/**
	 * thread safety
	 */
	public static void init(URL configurationFileURL) {
		if(cacheManager == null)
			cacheManager = CacheManager.create(configurationFileURL);
	}
	
	/**
	 * thread safety
	 */
	public static void init(InputStream inputStream) {
		if(cacheManager == null)
			cacheManager = CacheManager.create(inputStream);
	}
	
	/**
	 * thread safety
	 */
	public static void init(Configuration configuration) {
		if(cacheManager == null)
			cacheManager = CacheManager.create(configuration);
	}
	
	/**
	 * thread safety
	 */
	public static void destroy() {
		if(cacheManager != null) {
			synchronized (locker) {
				if(cacheManager != null) {
					cacheManager.shutdown();
					cacheManager = null;
				}
			}
		}
	}
	
	/**
	 * thread safety
	 */
	static Cache getOrAddCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			synchronized (locker) {
				cache = cacheManager.getCache(cacheName);
				if(cache == null) {
					LogKit.debug(String.format("cache \"%s\" not found!", cacheName));
					cacheManager.addCacheIfAbsent(cacheName);
					cache = cacheManager.getCache(cacheName);
					LogKit.debug(String.format("cache \"%s\" create success!", cacheName));
				}
			}
		} return cache;
	}
	
	/**
	 * thread safety
	 */
	public static void put(String cacheName, Object key, Object value) {
		getOrAddCache(cacheName).put(new Element(key, value));
	}
	
	/**
	 * thread safety
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key) {
		Element element = getOrAddCache(cacheName).get(key);
		return element != null ? (T)element.getObjectValue() : null;
	}
	
	/**
	 * thread safety
	 */
	public static void remove(String cacheName, Object key) {
		getOrAddCache(cacheName).remove(key);
	}
	
	/**
	 * thread safety
	 */
	public static void removeAll(String cacheName) {
		getOrAddCache(cacheName).removeAll();
	}
	
	/**
	 * thread safety
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key, ILoader loader) {
		Object data = get(cacheName, key);
		if (data == null) { data = loader.load();
			put(cacheName, key, data); } return (T)data;
	}
	
	/**
	 * thread safety
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key, Class<? extends ILoader> loaderClass) {
		Object data = get(cacheName, key);
		if (data == null) {
			try { ILoader loader = loaderClass.newInstance();
				data = loader.load(); put(cacheName, key, data);
			} catch (Exception e) { throw new RuntimeException(e); }
		} return (T)data;
	}
	
}
