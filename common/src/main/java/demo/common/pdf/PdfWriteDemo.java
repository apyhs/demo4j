package demo.common.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PdfWriteDemo {
    private File file = new File("E:\\HelloPDF.pdf");

    @Test
    public void createPDF() throws IOException {
        PDDocument pdDocument = new PDDocument();
        PDPage pdPage = new PDPage();
        pdDocument.addPage(pdPage);
        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Hello, World! ");
        contentStream.endText();

        contentStream.close();
        pdDocument.save(file);
        pdDocument.close();
    }

    @Test
    public void addImageToPDF() throws IOException {
        PDDocument doc = PDDocument.load(file);

        // we will add the image to the first page.
        PDPage page = doc.getPage(0);

        // createFromFile is the easiest way with an image file
        // if you already have the image in a BufferedImage,
        // call LosslessFactory.createFromImage() instead
        PDImageXObject pdImage = PDImageXObject.createFromFile(
                "E:\\Temp\\713348.png", doc);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page
                , PDPageContentStream.AppendMode.APPEND, true, true);

        // contentStream.drawImage(ximage, 20, 20 );
        // better method inspired by http://stackoverflow.com/a/22318681/535646
        // reduce this value if the image is too large
        float scale = 0.5f;
        contentStream.drawImage(pdImage, -70, 20
                , pdImage.getWidth() * scale, pdImage.getHeight() * scale);

        contentStream.close();
        doc.save(file);
    }

    @Test
    public void addJavascript() throws IOException {
        PDDocument document = PDDocument.load(file);
        PDActionJavaScript javascript = new PDActionJavaScript(
                "app.alert( {cMsg: 'Hello, World! ... ... ', nIcon: 3, nType: 0, cTitle: 'Hello, World! ' } );");
        document.getDocumentCatalog().setOpenAction( javascript );
        if(document.isEncrypted()) {
            throw new IOException( "Encrypted documents are not supported for this example" );
        }
        document.save(file);
        document.close();
    }

    @Test
    public void createComplicatedPDF() throws IOException {
        PDDocument pdDocument = new PDDocument();
        for (int i = 0; i < 10; i++) {
            PDPage pdPage = new PDPage();
            pdDocument.addPage(pdPage);
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(100, 700);
            StringBuilder builder = new StringBuilder(i).append(" ");
            for (int j = 0; j < 20; j++) {
                builder.append("Hello, World! ");
            }
            contentStream.showText(builder.toString());
            contentStream.endText();

            PDImageXObject pdImage = PDImageXObject.createFromFile(
                    "E:\\Temp\\713348.png", pdDocument);
            float scale = 0.5f;
            contentStream.drawImage(pdImage, -70, 20
                    , pdImage.getWidth() * scale, pdImage.getHeight() * scale);

            contentStream.close();
        }
        pdDocument.save(file);
        pdDocument.close();
    }

}
