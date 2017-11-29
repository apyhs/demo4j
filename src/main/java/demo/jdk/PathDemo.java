package demo.jdk;

import org.junit.Test;

import java.io.File;

public class PathDemo {

    @Test
    public void test1() {
        System.out.println(new File("c:\\window").isAbsolute());
        System.out.println(new File("c:\\window\\..\\system\\123.txt").isAbsolute());
        System.out.println(new File("window\\..\\system\\123.txt").isAbsolute());
        System.out.println(new File("c:/window/../system").isAbsolute());
        System.out.println(new File("/window/../system/123.txt").isAbsolute());
    }

    @Test
    public void test2() throws Exception {
        // root path
        String path = PathDemo.class.getResource("/").toURI().getPath();
        System.out.println(new File(path).getParentFile().getParentFile());
    }

    @Test
    public void test3() throws Exception {
        // class path
        ClassLoader classLoader = PathDemo.class.getClassLoader();
        System.out.println(classLoader.getResource("").toURI().getPath());
        System.out.println(new File(classLoader.getResource("").toURI().getPath()));
    }

    @Test
    public void test4() {
        Package p = PathDemo.class.getPackage();
        System.out.println(p.getName());
        System.out.println(p.getName().replaceAll("\\.", "/"));
    }

    @Test
    public void test5() {
        String str1 = "asd|sad|gds|hdfh|dfa";
        System.out.println(str1.replaceAll("\\|", ","));
        String str2 = "asd.sad.gds.hdfh.dfa";
        System.out.println(str2.replaceAll("\\.", "/"));
        String str3 = "asd.sad.gds.hdfh.dfa";
        System.out.println(str3.replaceAll("\\.", "\\\\"));
        System.out.println(System.getProperty("file.separator"));
        System.out.println("new File(\"\").toString()的结果为：" + new File("").toString());
    }

    @Test
    public void test6() {
        System.out.println(System.getProperty("file.separator"));
        System.out.println(System.getProperty("path.separator"));
    }



}
