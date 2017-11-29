package demo.demo.codec;

import org.junit.Test;

import java.util.Arrays;

public class HexDemo {

    @Test
    public void testHex() {
        byte[] data = {45,31,65,87,-48,-78,32,45,-45,12};
        System.out.println(Arrays.toString(data));
        System.out.println(encodeHex(data).toUpperCase());
        System.out.println(Arrays.toString(decodeHex("2d1f4157d0b2202dd30c")));
        System.out.println(Arrays.toString(decodeHex("2D1F4157D0B2202DD30C")));
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encodeHex(byte[] binaryData) {
        final int len = binaryData.length;
        final char[] out = new char[len << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = HEX_DIGITS[(0xF0 & binaryData[i]) >>> 4];
            out[j++] = HEX_DIGITS[0x0F & binaryData[i]];
        } return new String(out);
    }

    public static byte[] decodeHex(String encoded) {
        char[] encodedArr = encoded.toCharArray();
        final int len = encodedArr.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } final byte[] out = new byte[len >> 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(encodedArr[j], j) << 4; j++;
            f = f | toDigit(encodedArr[j], j); j++;
            out[i] = (byte) (f & 0xFF);
        } return out;
    }

    private static int toDigit(final char ch, final int index) {
        final int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        } return digit;
    }

}
