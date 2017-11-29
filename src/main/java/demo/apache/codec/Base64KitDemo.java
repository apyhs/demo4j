package demo.apache.codec;

import org.apache.commons.codec.binary.Base64;

public class Base64KitDemo {
	public static boolean isBase64(byte octet) {
		return Base64.isBase64(octet);
    }

    public static boolean isBase64(String base64) {
    	return Base64.isBase64(base64);
    }
    
    public static boolean isBase64(byte[] arrayOctet) {
    	return Base64.isBase64(arrayOctet);
    }
    
    public static byte[] encode(byte[] binaryData) {
    	return Base64.encodeBase64(binaryData);
    }
    
    public static byte[] encode(byte[] binaryData, boolean isChunked) {
    	return Base64.encodeBase64(binaryData, isChunked);
    }

    public static byte[] encode(byte[] binaryData, boolean isChunked, boolean urlSafe) {
    	return Base64.encodeBase64(binaryData, isChunked, urlSafe);
    }

    public static byte[] encode(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
    	return Base64.encodeBase64(binaryData, isChunked, urlSafe, maxResultSize);
    }

    public static String encodeString(byte[] binaryData) {
    	return Base64.encodeBase64String(binaryData);
    }
    
    public static byte[] encodeURLSafe(byte[] binaryData) {
    	return Base64.encodeBase64URLSafe(binaryData);
    }

    public static String encodeURLSafeString(byte[] binaryData) {
    	return Base64.encodeBase64URLSafeString(binaryData);
    }    

    public static byte[] encodeChunked(byte[] binaryData) {
    	return Base64.encodeBase64Chunked(binaryData);
    }

    public static byte[] decode(String base64String) {
    	return Base64.decodeBase64(base64String);
    }

    public static byte[] decode(byte[] base64Data) {
    	return Base64.decodeBase64(base64Data);
    }
}
