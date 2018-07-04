package demo.common.poi;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.core.IImageExtractor;

import java.io.*;

public class FileImageExtractorDemo implements IImageExtractor {
    private static final String WORD_MEDIA = "word/media/";

    private final File baseDir;

    public FileImageExtractorDemo(File baseDir) {
        this.baseDir = baseDir;
    }

    public void extract( String imagePath, byte[] imageData )
            throws IOException
    {
        // 是因为在 XHTMLMapper 中会默认追加 WORD_MEDIA = "word/media/"
        System.out.println(imagePath);
        if (imagePath.startsWith(WORD_MEDIA)) {
            imagePath = StringUtils.replace(imagePath, WORD_MEDIA, "");
        }

        this.extractWithEscape(imagePath, imageData);
    }

    private void extractWithEscape( String imagePath, byte[] imageData )
            throws IOException{
        File imageFile = new File( baseDir, imagePath );
        imageFile.getParentFile().mkdirs();
        InputStream in = null;
        OutputStream out = null;
        try
        {
            in = new ByteArrayInputStream( imageData );
            out = new FileOutputStream( imageFile );
            IOUtils.copyLarge( in, out );
        }
        finally
        {
            IOUtils.closeQuietly( in );
            IOUtils.closeQuietly( out );
        }
    }

}
