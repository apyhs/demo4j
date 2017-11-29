package demo.cache;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Dooo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<Integer> list = new ArrayList<>();
        Class<? extends List> aClass = list.getClass();
        Field f = aClass.getDeclaredField("elementData");
        f.setAccessible(true);
        Object o = f.get(list);
        System.out.println(o);
    }

}
