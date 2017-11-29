package demotest;

import demo.ObjKit;
import org.junit.Test;

import java.util.Arrays;

public class ObjKitTest {

    @Test
    public void test() {
        System.out.println(ObjKit.uuid());
    }

    @Test
    public void test1() {
        Class<?> type = (Class) Integer.class.getGenericSuperclass();
        Class<?> type1 = (Class) int.class.getGenericSuperclass();
        System.out.println(type);
        System.out.println(type1);
        System.out.println(int.class);
        System.out.println(Integer.class);
    }

    @Test
    public void test2() {
        byte[] bytes = new byte[10];
        System.out.println(ObjKit.isEmpty(bytes));
    }

    @Test
    public void test3() {
        char[] arr = {'g','d','r','u','r','s','v','n','f','e','s'};
        char[] arr1 = {'g','d','r','u','r','s','v','n','f','e','s'};
        ObjKit.ascSort(arr);
        ObjKit.descSort(arr1);
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(arr1));
    }

}
