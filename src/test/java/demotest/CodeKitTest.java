package demotest;

import demo.CodeKit;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodeKitTest {

    @Test
    public void test1() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "zhangsan");
        data.put("age", "15");
        data.put("mail", "zs@admin.com");
        System.out.println(CodeKit.encodeMap(data, ":", ","));
        System.out.println(CodeKit.encodeMap(data));
    }

    @Test
    public void test2() {
        Map<String, String> map = CodeKit.decodeMap("mail:zs@admin.com,name:zhangsan,age:15", ":", ",");
        Map<String, String> map1 = CodeKit.decodeMap("mail=zs@admin.com&age=15&name=zhangsan");
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() + " >>>> " + entry.getValue());
        }
        System.out.println("---------------------------------");
        Set<Map.Entry<String, String>> entries1 = map1.entrySet();
        for (Map.Entry<String, String> entry : entries1) {
            System.out.println(entry.getKey() + " >>>> " + entry.getValue());
        }
    }

}
