package demo.demo.jdk;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class StringDemo {

    /**
     * 跨平台换行符  >> line.separator
     */
    private static String ENDL = System.getProperty("line.separator");

    @Test
    public void testLSep() {
        System.out.println("111");
        System.out.print(ENDL);
        System.out.println("222");
        System.out.print(System.getProperty("line.separator"));
        System.out.println("333");
    }

    @Test
    public void testPrintException() {
        try {
            int num = 1 / 0;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            System.out.println(sw.toString());
        }
    }

    @Test
    public void removeRepeat(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "aa","bb","cc","dd","aa","bb");
        System.out.println(list);

        Set<String> set = new HashSet<>();
        set.addAll(list);
        list.clear();
        list.addAll(set);

        System.out.println(list);
    }

    @Test
    public void test1() {
        System.out.println("\n".trim().length());
        System.out.println("\t".trim().length());
        System.out.println("\r".trim().length());
        System.out.println(" ".trim().length());
    }

    @Test
    public void test2() {
        System.out.println("\n\t\n \n".trim());
        System.out.println("\n\t\n \n".trim().length() == 0);

        System.out.println("\n\t\n \naaa\n\t\n \n".trim());
        System.out.println("\n\t\n \naaa\n\t\n \n".trim().length() == 0);

        System.out.println("\na a\n\t\na \n".trim());
        System.out.println("\na a\n\t\na \n".trim().length() == 0);

        System.out.println("\n\n\t\n \n".trim());
        System.out.println("\n\n\t\n \n".trim().length() == 0);

        System.out.println("" == null);
    }

    @Test
    public void test2_1() {
        System.out.println(isBlankTest("\t\t"));
    }

    /**
     * trim方法耗性能，因此这样更加合适
     */
    public static boolean isBlankTest(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                // case '\b':
                // case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Test
    public void test4() {
        String nullStr = null;
        // 首先得判断是否有 null 存在
        // System.out.println(nullStr.equals(null));
        // System.out.println(equalsTest(nullStr, ""));
        // System.out.println(equalsTest(nullStr, null));

        // 引用相等，对应的值肯定相等
        String str1 = new String("hello,world!");
        String str2 = str1;
        // System.out.println(str1 == str2);
        // 在 equals 源码中已经对 引用相等有判断了
        System.out.println(str1.equals(str2));
    }

    public static boolean equalsTest(String s1, String s2) {
        // 首先判断 是否为 null
        if(s1 == null) {
            if(s2 == null) return true;
            else return false;
        } else {
            // s1 不为 null
            System.out.println("s1 不为 null");
            return s1.equals(s2);
        }
    }

    @Test
    public void test5() throws Exception {
        String aaa = "asd|fasf|asf|rth,tyew,hcsd,errhe";
        String[] split = aaa.split("\\|");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void test6() {
        String[] strs = {"zz", "bb", "rr", "jds", "asdfas"};
        Arrays.sort(strs);
        System.out.println(Arrays.toString(strs));
    }

}
