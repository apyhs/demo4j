package demo.common.poi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class WordDemo {

    @Test
    public void test1() throws Exception {
        poiWord07ToHtml(new File("E:\\TestWord.docx")
                , new File("E:\\TestWord")
                , new File("E:\\TestWord\\test1.html"));
    }

    @Test
    public void test2() throws Exception {
        poiWord03ToHtml(new File("E:\\TestWord.doc")
                , new File("E:\\TestWord")
                , new File("E:\\TestWord\\test2.html"));
    }

    @Test
    public void test3() throws IOException {
        String path = "E:\\TestWord\\test1.html";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        String toString = IOUtils.toString(reader);
        IOUtils.closeQuietly(reader);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        writer.write(StringEscapeUtils.unescapeHtml4(toString));
        IOUtils.closeQuietly(writer);
    }

    public static void poiWord07ToHtml(File docxFile, File imageFolderFile, File outputFile)
            throws IOException {
        if (!outputFile.exists()) { outputFile.getParentFile().mkdirs(); outputFile.createNewFile(); }
        // 读取文档内容
        InputStream in = new FileInputStream(docxFile);
        XWPFDocument document = new XWPFDocument(in);

        // 加载html页面时图片路径
        XHTMLOptions options = XHTMLOptions.create().URIResolver( new BasicURIResolverDemo("./"));
        // 图片保存文件夹路径
        options.setExtractor(new FileImageExtractorDemo(imageFolderFile));
        OutputStream out = new FileOutputStream(outputFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        out.close();
    }

    public static void poiWord03ToHtml(File docFile, File imageFolderFile, File outputFile)
            throws IOException, ParserConfigurationException, TransformerException {
        InputStream input = new FileInputStream(docFile);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType,
                                      String suggestedName, float widthInches, float heightInches) {
                File imageFile = new File( imageFolderFile, suggestedName );
                imageFile.getParentFile().mkdirs();
                InputStream in = null;
                OutputStream out = null;
                try
                {
                    in = new ByteArrayInputStream( content );
                    out = new FileOutputStream( imageFile );
                    IOUtils.copyLarge( in, out );
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally
                {
                    IOUtils.closeQuietly( in );
                    IOUtils.closeQuietly( out );
                }
                //图片在html页面加载路径
                return "./" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        //获取文档中所有图片
//        List pics = wordDocument.getPicturesTable().getAllPictures();
//        if (pics != null) {
//            for (int i = 0; i < pics.size(); i++) {
//                Picture pic = (Picture) pics.get(i);
//                try {//图片保存在文件夹的路径
//                    File file = new File(imageFolderFile, pic.suggestFullFileName());
//                    file.getParentFile().mkdirs();
//                    file.createNewFile();
//                    pic.writeImageContent(new FileOutputStream(file));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        //创建html页面并将文档中内容写入页面
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toString("UTF-8"));
        FileUtils.writeStringToFile(outputFile, content, "utf-8");

    }


}
