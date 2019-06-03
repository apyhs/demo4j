package demo.barcode;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class QRCodeDemo {

    /**
     * 生成 二维码
     */
    @Test
    public void test1() {
        try {
            String content = "http://anmt.me";
            String path = "C:/Users/admin/Desktop";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
            File file1 = new File(path,"123.png");
            MatrixToImageWriter.writeToFile(bitMatrix, "png", file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析 二维码
     */
    @Test
    public void test2() {
        try {
            MultiFormatReader formatReader = new MultiFormatReader();
            File file = new File("C:/Users/admin/Desktop/123.png");
            BufferedImage image = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            Result result = formatReader.decode(binaryBitmap, hints);

            System.out.println("result = "+ result.toString());
            System.out.println("resultFormat = "+ result.getBarcodeFormat());
            System.out.println("resultText = "+ result.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
