package demotest;

import demo.RandomKit;
import org.junit.Test;

public class RandomKitTest {

    @Test
    public void test1() {
        System.out.println(RandomKit.generateNum(10));
    }

    @Test
    public void test2() {
        System.out.println(RandomKit.aesIv());
        System.out.println(RandomKit.aesKey());
        System.out.println(RandomKit.blowfishIv());
        System.out.println(RandomKit.blowfishKey());
        System.out.println(RandomKit.desedeIv());
        System.out.println(RandomKit.desedeKey());
        System.out.println(RandomKit.desIv());
        System.out.println(RandomKit.desKey());
        System.out.println(RandomKit.generateNum(6));
        System.out.println(RandomKit.generateSeq(6));
    }

}
