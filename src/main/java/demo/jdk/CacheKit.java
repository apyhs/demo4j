package demo.jdk;

import java.util.concurrent.ConcurrentHashMap;

public class CacheKit {
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> cache = new ConcurrentHashMap<>();
    private static final String root = "root";

    public static ConcurrentHashMap<String, Object> use(){
        return use(root);
    }

    public static ConcurrentHashMap<String, Object> use(String cacheName){
        ConcurrentHashMap<String, Object> map = cache.get(cacheName);
        if (map == null) {
            synchronized (CacheKit.class) {
                map = cache.get(cacheName);
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                    cache.put(cacheName, map);
                }
            }
        }
        // 在这的时候，切换到 remove，还是会导致不一致的问题
        // 所以 同步方法 是最好的选择，但是 性能 会很差
        return map;
    }

    public static void remove(String cacheName) {
        synchronized (CacheKit.class) {
            cache.remove(cacheName);
        }
    }


}
