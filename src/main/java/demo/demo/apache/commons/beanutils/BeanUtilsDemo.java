package demo.demo.apache.commons.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BeanUtilsDemo {

    @Test
    public void test1() throws Exception {
        Person p1 = new Person().setName("张三").setAge(19).setSex(1);
        Map<String, String> map = BeanUtils.describe(p1);
        map.remove("class"); // 移除class
        System.out.println(map);

        Map<String, Object> map1 = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
                map1.put(entry.getKey(), entry.getValue());
        }

        Person p2 = new Person();
        BeanUtils.populate(p2, map1);// 进不去
        System.out.println(p2);
    }

}
