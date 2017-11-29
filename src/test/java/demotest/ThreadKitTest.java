package demotest;

import demo.ThreadKit;
import org.junit.Test;

public class ThreadKitTest {

    @Test
    public void sleepTest(){
        System.out.println("1");
        ThreadKit.sleep(10000);
        System.out.println("2");
    }

    @Test
    public void currentMethodNameTest(){
        System.out.println(ThreadKit.currentMethodName());
    }

    @Test
    public void currentClassNameTest(){
        System.out.println(ThreadKit.currentClassName());
    }

    @Test
    public void currentFileNameTest(){
        System.out.println(ThreadKit.currentFileName());
    }

    @Test
    public void currentLineNumberTest() {
        System.out.println(ThreadKit.currentLineNumber());
    }
}
