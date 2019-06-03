package demo.document.word;

import artoria.util.Assert;
import demo.document.common.AbstractConverter;
import demo.document.common.ImageExtractor;
import demo.document.common.URIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Docx file converter.
 * @author Kahle
 */
public class DocxFileConverter extends AbstractConverter {

    @Override
    public void convertToHtml(InputStream fileInputStream, OutputStream htmlOutputStream
            , String charset, URIResolver resolver, ImageExtractor extractor) throws Exception {
        Assert.notNull(fileInputStream, "Parameter \"fileInputStream\" must not null. ");
        Assert.notNull(htmlOutputStream, "Parameter \"htmlOutputStream\" must not null. ");
        Assert.notNull(charset, "Parameter \"charset\" must not blank. ");
        Assert.notNull(resolver, "Parameter \"resolver\" must not null. ");
        Assert.notNull(extractor, "Parameter \"extractor\" must not null. ");
        XWPFDocument document = new XWPFDocument(fileInputStream);
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new URIResolverAdapter(resolver));
        options.setExtractor(new ImageExtractorAdapter(extractor));
        XHTMLConverter.getInstance().convert(document, htmlOutputStream, options);
    }

}
