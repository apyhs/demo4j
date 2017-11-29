package demo;

import java.lang.reflect.Method;

public class CodeKit {

    public static String encodeBase64(byte[] input) {
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method encode = clazz.getMethod("encode", byte[].class);
            encode.setAccessible(true);
            Object object = encode.invoke(null, new Object[]{input});
            return (String) object;
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] decodeBase64(String input) {
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method decode = clazz.getMethod("decode", String.class);
            decode.setAccessible(true);
            Object object = decode.invoke(null, input);
            return (byte[]) object;
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        char[] chars = string.toCharArray();
        for (char c : chars)
            unicode.append("\\u" + Integer.toHexString(c));
        return unicode.toString();
    }

    public static String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++)
            string.append((char)Integer.parseInt(hex[i], 16));
        return string.toString();
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encodeHex(byte[] data) {
        return new String(CodeKit.encodeHex0(data));
    }

    private static char[] encodeHex0(byte[] data) {
        int len = data.length;
        char[] out = new char[len << 1];
        // two characters form the hex0 value.
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = HEX_DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = HEX_DIGITS[0x0F & data[i]];
        } return out;
    }
    
    public static byte[] decodeHex(String data) {
    	return decodeHex0(data.toCharArray());
    }

    private static byte[] decodeHex0(char[] data) {
        try {
			int len = data.length;
			if ((len & 0x01) != 0) {
			    throw new RuntimeException("odd number of characters!");
			}
			byte[] out = new byte[len >> 1];
			// two characters form the hex0 value.
			for (int i = 0, j = 0; j < len; i++) {
			    int f = toDigit(data[j], j) << 4; j++;
			    f = f | toDigit(data[j], j); j++;
			    out[i] = (byte) (f & 0xFF);
			} return out;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character!");
        }  return digit;
    }
}
