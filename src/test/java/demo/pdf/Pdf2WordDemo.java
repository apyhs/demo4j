package demo.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class Pdf2WordDemo {

    @Test
    public void test1() throws IOException {
        String pdf = "E:\\TestWord.pdf";
        XWPFDocument doc = new XWPFDocument();

        PdfReader reader = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            TextExtractionStrategy strategy =
                    parser.processContent(i, new SimpleTextExtractionStrategy());
            String text = strategy.getResultantText();
            XWPFParagraph p = doc.createParagraph();
            XWPFRun run = p.createRun();
            run.setText(text);
            run.addBreak(BreakType.PAGE);
        }
        FileOutputStream out = new FileOutputStream("E:\\TestWord_out.docx");
        doc.write(out);

        reader.close();
        doc.close();
        out.close();
    }

}
