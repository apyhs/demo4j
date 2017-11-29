package demo.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexUtils {

    public static char[] encodeHex(byte[] data) {
        return Hex.encodeHex(data);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return Hex.encodeHex(data, toLowerCase);
    }

    public static String encodeHexString(byte[] data) {
        return Hex.encodeHexString(data);
    }

    public static byte[] decodeHex(char[] data) throws DecoderException {
        return Hex.decodeHex(data);
    }

}
