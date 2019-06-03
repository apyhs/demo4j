package demo.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PdfReadDemo {
    private File file = new File("E:\\TestWord.pdf");

    @Test
    public void readText() throws IOException {
        PDDocument pdDocument = PDDocument.load(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        System.out.println(pdfTextStripper.getText(pdDocument));
        pdDocument.close();
    }

    @Test
    public void readHtml() throws IOException {
        PDDocument pdDocument = PDDocument.load(file);
        PDFText2HTML pdfText2HTML = new PDFText2HTML();
        System.out.println(pdfText2HTML.getText(pdDocument));
        pdDocument.close();
    }

    @Test
    public void readImage() throws IOException {
        PDFStreamEngineDemo.extract(file.toString(), null);
    }

    @Test
    public void readHtmlWithImageTry() throws IOException {
        PDDocument pdDocument = PDDocument.load(file);
        PDFHtmlWithImageDemo pdfText2HTMLDemo = new PDFHtmlWithImageDemo();
        System.out.println(pdfText2HTMLDemo.getText(pdDocument));
        pdDocument.close();
    }

}
