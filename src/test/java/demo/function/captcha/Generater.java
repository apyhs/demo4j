package demo.function.captcha;

import org.junit.Test;

import java.io.IOException;

public class Generater {

    @Test
    public void test1() {
        StringCaptcha vCode = new StringCaptcha(160,40,5,150);
        try {
            String path="D:/" + System.currentTimeMillis() + ".png";
            System.out.println(vCode.getCode() + " >" + path);
            vCode.write(path);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
