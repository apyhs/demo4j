package demo.jdk;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalDemo {
    private static ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    @Test
    public void test1() {
        Map<String, Object> map = tl.get();
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("123", "123");
        tl.set(map);

        System.out.println(tl.get());

        tl.remove();

        Map<String, Object> map1 = tl.get();
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        map1.put("456", "456");
        tl.set(map1);

        System.out.println(tl.get());
    }


}
