package demo.document.pdf;

import artoria.io.IOUtils;
import demo.document.common.AbstractConverter;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Ignore
public class PDFConverterTest {
    private File file = new File("E:\\Temp\\Test\\Test.pdf");
    private AbstractConverter converter = new PDFConverter();

    @Test
    public void test1() throws Exception {
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream("E:\\Temp\\Test\\pdf_test.html");
        converter.convertToHtml(in, out, "utf-8"
                , "./", new File("E:\\Temp\\Test"));
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(in);
    }

}
