package demo.demo.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class QRCodeDemo1 {

    @Test
    public void test1() throws WriterException, IOException {
        int width = 200;
        int height = 200;
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        FileOutputStream out = new FileOutputStream("d:\\aa.png");

        QRCodeWriter e = new QRCodeWriter();
        BitMatrix bitMatrix = e.encode("123", BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
    }
}
