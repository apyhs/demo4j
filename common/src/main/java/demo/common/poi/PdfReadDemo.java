package demo.common.poi;

import artoria.io.IOUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class PdfReadDemo {
    private File file = new File("E:\\HelloPDF.pdf");

    @Test
    public void readPDF() throws IOException {
        PDDocument pdDocument = PDDocument.load(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        System.out.println(pdfTextStripper.getText(pdDocument));
        pdDocument.close();
    }

    @Test
    public void readImage() throws IOException {
        PDDocument document = PDDocument.load(file);
        int pagesSize = document.getNumberOfPages();

        System.out.println("getNumberOfPages ===============>> " + pagesSize);
        int count = 0;

        for (int i = 0; i < pagesSize; i++) {
            PDPage page = document.getPage(i);
            PDResources resources = page.getResources();
            Iterable<COSName> xobjects = resources.getXObjectNames();

            if (xobjects == null) { continue; }
            Iterator<COSName> imageIter = xobjects.iterator();
            while (imageIter.hasNext()) {
                COSName key = imageIter.next();
                if (resources.isImageXObject(key)) {
                    PDImageXObject image = (PDImageXObject) resources.getXObject(key);
                    File file = new File("E:\\Temp\\outpot_"+count+".png");
                    FileOutputStream out = new FileOutputStream(file);
                    InputStream in = image.createInputStream();
                    IOUtils.copyLarge(in, out);
                    IOUtils.closeQuietly(out);
                    IOUtils.closeQuietly(in);
                    count++;
                }
            }
        }

        System.out.println(count);
    }

}
