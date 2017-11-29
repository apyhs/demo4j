package demo.jdk;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

public class FileTypeDemo {

    @Test
    public void test1() throws Exception {
        byte[] doc = FileUtils.readFileToByteArray(new File("e:\\1.doc"));
        byte[] xls = FileUtils.readFileToByteArray(new File("e:\\1.xls"));
        int len = 1033; // 1034 不相同
        System.out.println(Hex.encodeHexString(doc).toUpperCase().substring(0, len));
        System.out.println();
        System.out.println();
        System.out.println(Hex.encodeHexString(xls).toUpperCase().substring(0, len).equals(Hex.encodeHexString(doc).toUpperCase().substring(0, len)));
    }

    @Test
    public void test2() throws Exception {
        byte[] rtf = FileUtils.readFileToByteArray(new File("e:\\1.css"));
        System.out.println(Hex.encodeHexString(rtf).toUpperCase());
    }

    @Test
    public void testz() throws Exception {
        String str = "504B03040A0000000000";
        System.out.println(str.toUpperCase());
        System.out.println(str.length());
    }

}
