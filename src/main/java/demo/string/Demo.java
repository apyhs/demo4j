package demo.string;

import kit4j.StrKit;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Demo {

    @Test
    public void test1(){
        List<String> list = StrKit.list("aa", "bb", "cc", "aa", "bb", "ee", "aa", "bb");
        System.out.println(list);
        StrKit.removeRepeat(list);
        System.out.println(list);
    }

    @Test
    public void test2(){
        String str = "abcdabceinac";
        System.out.println(str);
        str = StrKit.removeRepeat(str);
        System.out.println(str);
        System.out.println(StrKit.randomSort(str));
    }

    @Test
    public void test3(){
        List<String> list = StrKit.list("fgnvd", "asdwd", "azxbf", "rtuhgb", "fvbxf", "llyng");
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void test4(){
        String[] data = {"fgnvd", "asdwd", "azxbf", "rtuhgb", "fvbxf", "llyng"};
        System.out.println(Arrays.toString(data));
        StrKit.dictSort(data);
        System.out.println(Arrays.toString(data));
    }

}
