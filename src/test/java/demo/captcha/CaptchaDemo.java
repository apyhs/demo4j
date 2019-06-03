package demo.captcha;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CaptchaDemo {

    @Test
    public void test() throws IOException {
        StringCaptcha captcha = new StringCaptcha(120, 90);
        File file = new File("d:\\1.png"); file.createNewFile();
        captcha.write(new FileOutputStream(file));
    }

}
