package demotest;

import demo.EncrKit;
import org.junit.Test;

public class EncrKitTest {

    @Test
    public void test1() {
        EncrKit.desEcbPkcs5Encrypt(new byte[0], new byte[0]);
        EncrKit.desEcbPkcs5Decrypt(new byte[0], new byte[0]);
        EncrKit.desCbcPkcs5Encrypt(new byte[0], new byte[0], new byte[0]);
        EncrKit.desCbcPkcs5Decrypt(new byte[0], new byte[0], new byte[0]);
        EncrKit.aesEcbPkcs5Encrypt(new byte[0], new byte[0]);
        EncrKit.aesEcbPkcs5Decrypt(new byte[0], new byte[0]);
        EncrKit.blowfishEcbPkcs5Encrypt(new byte[0], new byte[0]);
        EncrKit.blowfishEcbPkcs5Decrypt(new byte[0], new byte[0]);
    }

}
