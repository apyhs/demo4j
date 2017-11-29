package demo;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * properties kit
 */
public class PropKit {
	private static final ConcurrentHashMap<String, Properties> cache = new ConcurrentHashMap<>();
	private static String defaultCharsetName = Charset.defaultCharset().name();

	public static void setDefCharsetName(String charset){
		if (StrKit.isBlank(charset)) {
			throw new IllegalArgumentException("charset name can not be blank!");
		} defaultCharsetName = charset;
	}

	public static void clear() {
		cache.clear();
	}

	public boolean containsKey(String fileName, String key) {
		return use(fileName).containsKey(key);
	}

	public static String get(String fileName, String key) {
		return use(fileName).getProperty(key);
	}

	public static String get(String fileName, String key, String defaultValue) {
		return use(fileName).getProperty(key, defaultValue);
	}

	public Integer getInt(String fileName, String key) {
		return this.getInt(fileName, key, (Integer)null);
	}

	public Integer getInt(String fileName, String key, Integer defaultValue) {
		String value = use(fileName).getProperty(key);
		return value != null ? Integer.valueOf(Integer.parseInt(value.trim())) : defaultValue;
	}

	public Long getLong(String fileName, String key) {
		return this.getLong(fileName, key, (Long)null);
	}

	public Long getLong(String fileName, String key, Long defaultValue) {
		String value = use(fileName).getProperty(key);
		return value != null ? Long.valueOf(Long.parseLong(value.trim())) : defaultValue;
	}

	public Boolean getBoolean(String fileName, String key) {
		return this.getBoolean(fileName, key, (Boolean)null);
	}

	public Boolean getBoolean(String fileName, String key, Boolean defaultValue) {
		String value = use(fileName).getProperty(key);
		if(value != null) {
			value = value.toLowerCase().trim();
			if("true".equals(value)) {
				return Boolean.valueOf(true);
			} else if("false".equals(value)) {
				return Boolean.valueOf(false);
			} else {
				throw new IllegalArgumentException("The value can not parse to Boolean : " + value);
			}
		} else {
			return defaultValue;
		}
	}

	public static Properties use(String fileName) {
		return use(fileName, defaultCharsetName);
	}

	public static Properties use(String fileName, String charset) {
		Properties result = cache.get(fileName);
		if(result == null) {
			result = useless(fileName, charset);
			cache.put(fileName, result);
		} return result;
	}

	public static Properties useless(String fileName) {
		return useless(fileName, defaultCharsetName);
	}

	public static Properties useless(String fileName, String charset) {
		try {
			Thread thread = Thread.currentThread();
			ClassLoader loader = thread.getContextClassLoader();
			InputStream in = loader.getResourceAsStream(fileName);
			if(in == null) {
				throw new IllegalArgumentException("File not found in classpath : " + fileName);
			} return load(in, charset);
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Properties load(String file) {
		return load(new File(file));
	}

	public static Properties load(String file, String charset) {
		return load(new File(file), charset);
	}

	public static Properties load(File file) {
		try {
			return load(new FileInputStream(file));
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Properties load(File file, String charset) {
		try {
			return load(new FileInputStream(file), charset);
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Properties load(InputStream in) throws IOException {
		return load(in, defaultCharsetName);
	}

	public static Properties load(InputStream in, String charset) throws IOException {
		Properties prop = new Properties();
		InputStreamReader reader = new InputStreamReader(in, charset);
		prop.load(reader); StreamKit.close(reader); return prop;
	}

}
