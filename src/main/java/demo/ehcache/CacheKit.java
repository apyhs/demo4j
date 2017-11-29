package demo.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.Configuration;
import saber.jdk.LogKit;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class CacheKit {
	private static CacheManager cacheManager;
	private static Object locker = new Object();

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public static void init() {
		cacheManager = CacheManager.create();
	}

	public static void init(String configurationFileName) {
		cacheManager = CacheManager.create(configurationFileName);
	}

	public static void init(URL configurationFileURL) {
		cacheManager = CacheManager.create(configurationFileURL);
	}

	public static void init(InputStream inputStream) {
		cacheManager = CacheManager.create(inputStream);
	}

	public static void init(Configuration configuration) {
		cacheManager = CacheManager.create(configuration);
	}

	public static void destroy() {
		cacheManager.shutdown();
	}

	static Cache getOrAddCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if(cache == null) {
			synchronized (locker) {
				cache = cacheManager.getCache(cacheName);
				if(cache == null) {
					LogKit.warn("Could not find cache config [" + cacheName + "], using default.");
					cacheManager.addCacheIfAbsent(cacheName);
					cache = cacheManager.getCache(cacheName);
					LogKit.debug("Cache [" + cacheName + "] started.");
				}
			}
		} return cache;
	}

	@SuppressWarnings("rawtypes")
	public static List getKeys(String cacheName) {
		return getOrAddCache(cacheName).getKeys();
	}

	public static void put(String cacheName, Object key, Object value) {
		getOrAddCache(cacheName).put(new Element(key, value));
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key) {
		Element element = getOrAddCache(cacheName).get(key);
		return element != null ? (T) element.getObjectValue() : null;
	}

	public static void remove(String cacheName, Object key) {
		getOrAddCache(cacheName).remove(key);
	}

	public static void removeAll(String cacheName) {
		getOrAddCache(cacheName).removeAll();
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key, IDataLoader dataLoader) {
		Object data = get(cacheName, key);
		if (data == null) {
			data = dataLoader.load();
			put(cacheName, key, data);
		} return (T) data;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String cacheName, Object key, Class<? extends IDataLoader> dataLoaderClass) {
		Object data = get(cacheName, key);
		if (data == null) {
			try {
				IDataLoader dataLoader = dataLoaderClass.newInstance();
				data = dataLoader.load();
				put(cacheName, key, data);
			} catch (Exception e) { throw new RuntimeException(e); }
		} return (T) data;
	}

}
