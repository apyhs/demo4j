package demo.base;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropUtils {
    private static final String defaultCharsetName = Charset.defaultCharset().name();
    private static final ConcurrentHashMap<String, Properties> cache = new ConcurrentHashMap<>();

    public Integer getInt(String fileName, String key) throws IOException {
        return getInt(fileName, key, null);
    }

    public Integer getInt(String fileName, String key, Integer defaultValue) throws IOException {
        String value = use(fileName).getProperty(key);
        return value != null ? Integer.parseInt(value.trim()) : defaultValue;
    }

    public Long getLong(String fileName, String key) throws IOException {
        return getLong(fileName, key, null);
    }

    public Long getLong(String fileName, String key, Long defaultValue) throws IOException {
        String value = use(fileName).getProperty(key);
        return value != null ? Long.parseLong(value.trim()) : defaultValue;
    }

    public Boolean getBoolean(String fileName, String key) throws IOException {
        return getBoolean(fileName, key, null);
    }

    public Boolean getBoolean(String fileName, String key, Boolean defaultValue) throws IOException {
        String value = use(fileName).getProperty(key);
        if (value != null) {
            value = value.toLowerCase().trim();
            if ("true".equals(value)) {
                return true;
            } else if ("false".equals(value)) {
                return false;
            } else {
                throw new IllegalArgumentException("The value can not parse to Boolean: " + value);
            }
        } else {
            return defaultValue;
        }
    }

    public static String get(String fileName, String key) throws IOException {
        return use(fileName).getProperty(key);
    }

    public static String get(String fileName, String key, String defaultValue) throws IOException {
        return use(fileName).getProperty(key, defaultValue);
    }

    public boolean containsKey(String fileName, String key) throws IOException {
        return use(fileName).containsKey(key);
    }

    public static Properties remove(String fileName) {
        return cache.remove(fileName);
    }

    public static void clear() {
        cache.clear();
    }

    public static Properties use(String fileName) throws IOException {
        return use(fileName, defaultCharsetName);
    }

    public static Properties use(String fileName, String charset) throws IOException {
        Properties result = cache.get(fileName);
        if(result == null) {
            result = load(fileName, charset);
            cache.put(fileName, result);
        }
        return result;
    }

    public static Properties load(String fileName) throws IOException {
        return load(fileName, defaultCharsetName);
    }

    public static Properties load(String fileName, String charset) throws IOException {
        InputStream in = null;
        try {
            Thread thread = Thread.currentThread();
            ClassLoader loader = thread.getContextClassLoader();
            in = loader.getResourceAsStream(fileName);
            if(in == null) {
                throw new IllegalArgumentException("File not found in classpath: " + fileName);
            }
            return load(in, charset);
        } finally {
            StreamUtils.close(in);
        }
    }

    public static Properties load(File file) throws IOException {
        return load(file, defaultCharsetName);
    }

    public static Properties load(File file, String charset) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            return load(in, charset);
        } finally {
            StreamUtils.close(in);
        }
    }

    public static Properties load(InputStream in) throws IOException {
        return load(in, defaultCharsetName);
    }

    public static Properties load(InputStream in, String charset) throws IOException {
        Properties prop = new Properties();
        Reader reader = new InputStreamReader(in, charset);
        prop.load(reader);
        return prop;
    }

}
