package demo.codec;

import org.apache.commons.codec.binary.Base64;

public class Base64Utils {

    public static boolean isBase64(byte octet) {
        return Base64.isBase64(octet);
    }

    public static boolean isBase64(byte[] arrayOctet) {
        return Base64.isBase64(arrayOctet);
    }

    public static boolean isBase64(String base64) {
        return Base64.isBase64(base64);
    }

    public static byte[] encodeBase64Chunked(byte[] binaryData) {
        return Base64.encodeBase64Chunked(binaryData);
    }

    public static String encodeBase64URLSafeString(byte[] binaryData) {
        return Base64.encodeBase64URLSafeString(binaryData);
    }

    public static byte[] encodeBase64URLSafe(byte[] binaryData) {
        return Base64.encodeBase64URLSafe(binaryData);
    }

    public static String encodeBase64String(byte[] binaryData) {
        return Base64.encodeBase64String(binaryData);
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
        return Base64.encodeBase64(binaryData, isChunked);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe) {
        return Base64.encodeBase64(binaryData, isChunked, urlSafe);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
        return Base64.encodeBase64(binaryData, isChunked, urlSafe, maxResultSize);
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        return Base64.decodeBase64(base64Data);
    }

    public static byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String);
    }

}
