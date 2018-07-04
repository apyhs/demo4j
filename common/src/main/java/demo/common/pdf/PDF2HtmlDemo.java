package demo.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.fit.pdfdom.resource.HtmlResourceHandler;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class PDF2HtmlDemo {
    private File file = new File("E:\\TestWord.pdf");

    @Test
    public void test1() throws IOException, ParserConfigurationException {
        PDDocument pdf = PDDocument.load(file);
        Writer output = new PrintWriter("E:\\TestWord.html", "utf-8");

        HtmlResourceHandler imageHandler = PDFDomTreeConfig.saveToDirectory(new File("E:\\TestWord"));
        PDFDomTreeConfig config = PDFDomTreeConfig.createDefaultConfig();
        config.setImageHandler(imageHandler);
        PDFDomTreeDemo pdfDomTree = new PDFDomTreeDemo(config);
//        PDFDomTree pdfDomTree = new PDFDomTree(config);

        pdfDomTree.writeText(pdf, output);
        output.close();
    }

}
