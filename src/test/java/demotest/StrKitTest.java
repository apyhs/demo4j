package demotest;

import demo.StrKit;
import org.junit.Test;

public class StrKitTest {

    @Test
    public void isBlankTest() {
        System.out.println(StrKit.isBlank(null));
        System.out.println(StrKit.isBlank("\n\t\r \n"));
        System.out.println(StrKit.isBlank("\n\t\n \naaa\n\t\n \n"));
        System.out.println(StrKit.isBlank("\na a\n\t\na \n"));
        System.out.println(StrKit.isBlank("\n\n\ta\n \n"));
    }

    @Test
    public void notBlankTest() {
        System.out.println(StrKit.notBlank(null));
        System.out.println(StrKit.notBlank("\n\t\n \n"));
        System.out.println(StrKit.notBlank("\n\t\n \naaa\n\t\n \n"));
        System.out.println(StrKit.notBlank("\na a\n\t\na \n"));
        System.out.println(StrKit.notBlank("\n\n\ta\n \n"));
    }

    @Test
    public void equalsTest() {
        String nullStr = null;
        String str1 = new String("Hello, World!");
        String str2 = str1;
        String str3 = "你好";
        String str4 = new String("你好");
        String str5 = new String("世界");
        System.out.println(StrKit.equals(nullStr, null)); // true
        System.out.println(StrKit.equals(nullStr, str1)); // false
        System.out.println(StrKit.equals(str1, str2)); // true
        System.out.println(StrKit.equals(str3, str4)); // true
        System.out.println(StrKit.equals(str3, str5)); // false
        System.out.println(StrKit.equals(str4, str5)); // false
    }

}
