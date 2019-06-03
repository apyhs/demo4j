package demo.codec;

/**
 * Base64 工具.
 * @author Kahle
 */
public class Base64Utils {

    public static String encodeBase64(byte[] data) {

        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data);
    }

    public static byte[] decodeBase64(String data) {

        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(data);
    }

}
