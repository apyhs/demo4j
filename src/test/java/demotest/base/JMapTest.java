package demotest.base;

import org.junit.Test;
import demo.base.JMap;

import java.util.HashMap;
import java.util.Map;

public class JMapTest {

    @Test
    public void test1() {
        Map<String, Integer> map = new HashMap<>();
        JMap.create(map);
    }

}
