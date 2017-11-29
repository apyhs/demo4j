package demotest;

import demo.PathKit;
import org.junit.Test;

public class PathKitTest {

    @Test
    public void test1() {
        System.out.println(PathKit.FSEP);
        System.out.println(PathKit.PSEP);
        System.out.println(PathKit.name("c:\\window\\123.txt"));
        System.out.println(PathKit.name("c:\\window\\system32"));
        System.out.println(PathKit.suffix("c:\\window\\123.txt"));
        System.out.println(PathKit.suffix("c:\\window\\123.txt.bak"));
        System.out.println(PathKit.suffix("c:\\window\\123"));
        System.out.println(PathKit.parent("c:\\user\\123"));
        System.out.println(PathKit.parent("c:\\window\\123.txt"));
        System.out.println(PathKit.parent("window\\system32\\123"));
    }

    @Test
    public void test2() {
        System.out.println(PathKit.abs("c:\\window\\123.txt"));
        System.out.println(PathKit.abs("window\\..\\system\\123.txt"));
        System.out.println(PathKit.abs("c:/window/../system"));
        System.out.println(PathKit.abs("/window/../system/123.txt"));

        System.out.println(PathKit.absAbs("c:\\window\\123.txt"));
        System.out.println(PathKit.absAbs("window\\..\\system\\123.txt"));
        System.out.println(PathKit.absAbs("c:/window/../system"));
        System.out.println(PathKit.absAbs("/window/../system/123.txt"));

        System.out.println(PathKit.isAbs("c:\\window"));
        System.out.println(PathKit.isAbs("c:\\window\\..\\system\\123.txt"));
        System.out.println(PathKit.isAbs("window\\..\\system\\123.txt"));
        System.out.println(PathKit.isAbs("c:/window/../system"));
        System.out.println(PathKit.isAbs("/window/../system/123.txt"));
    }

    @Test
    public void test3() {
        System.out.println(PathKit.classPath());
        System.out.println(PathKit.rootPath());
        System.out.println(PathKit.clazzPath(PathKit.class));
        System.out.println(PathKit.clazzPath(PathKitTest.class));
        System.out.println(PathKit.packagePath(PathKit.class));
        System.out.println(PathKit.packagePath(PathKitTest.class));
    }

}
