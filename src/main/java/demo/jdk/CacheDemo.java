package demo.jdk;

import java.util.ArrayList;
import java.util.List;

public class CacheDemo {

    public void test1() {
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.addAll(list.subList(0, 4));
        list2.addAll(list.subList(4, 10));
    }

}
