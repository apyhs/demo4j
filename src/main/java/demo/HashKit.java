package demo;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HashKit {
	private static String DEFAULT_CHARSET_NAME = "UTF-8";
	
	public static void setDefaultCharsetName(String charset){
		if (StrKit.isBlank(charset)) {
			throw new IllegalArgumentException("charset can not be blank!");
		} DEFAULT_CHARSET_NAME = charset;
	}
	
	public static String md5(String src){
		return md5(DEFAULT_CHARSET_NAME, src);
	}
	
	public static String md5(String charset, String src){
		return hash("MD5", charset, src);
	}
	
	public static String sha1(String src){
		return sha1(DEFAULT_CHARSET_NAME, src);
	}
	
	public static String sha1(String charset, String src){
		return hash("SHA-1", charset, src);
	}
	
	public static String sha256(String src){
		return sha256(DEFAULT_CHARSET_NAME, src);
	}
	
	public static String sha256(String charset, String src){
		return hash("SHA-256", charset, src);
	}
	
	public static String sha384(String src){
		return sha384(DEFAULT_CHARSET_NAME, src);
	}
	
	public static String sha384(String charset, String src){
		return hash("SHA-384", charset, src);
	}
	
	public static String sha512(String src){
		return sha512(DEFAULT_CHARSET_NAME, src);
	}
	
	public static String sha512(String charset, String src){
		return hash("SHA-512", charset, src);
	}
	
	public static String hmd5(String key, String src){
		return hmd5(DEFAULT_CHARSET_NAME, key, src);
	}
	
	/**
	 * hmac md5
	 */
	public static String hmd5(String charset, String key, String src){
		return hmac("HmacMD5", charset, key, src);
	}
	
	public static String hsha1(String key, String src){
		return hsha1(DEFAULT_CHARSET_NAME, key, src);
	}
	
	/**
	 * hmac sha1
	 */
	public static String hsha1(String charset, String key, String src){
		return hmac("HmacSHA1", charset, key, src);
	}
	
	public static String hsha256(String key, String src){
		return hsha256(DEFAULT_CHARSET_NAME, key, src);
	}
	
	/**
	 * hmac sha256
	 */
	public static String hsha256(String charset, String key, String src){
		return hmac("HmacSHA256", charset, key, src);
	}
	
	public static String hsha384(String key, String src){
		return hsha384(DEFAULT_CHARSET_NAME, key, src);
	}
	
	/**
	 * hmac sha384
	 */
	public static String hsha384(String charset, String key, String src){
		return hmac("HmacSHA384", charset, key, src);
	}
	
	public static String hsha512(String key, String src){
		return hsha512(DEFAULT_CHARSET_NAME, key, src);
	}
	
	/**
	 * hmac sha512
	 */
	public static String hsha512(String charset, String key, String src){
		return hmac("HmacSHA512", charset, key, src);
	}
	
	/**
	 * @see #hmac(String, byte[], byte[])
	 */
	public static String hmac(String algorithm, String charset, String key, String src) {
		try {
			return CodeKit.encodeHex(hmac(algorithm, key.getBytes(charset), src.getBytes(charset)));
		} catch (Exception e) { throw new RuntimeException(e); } 
	}
	
	/**
	 * @see #hash(String, byte[])
	 */
	public static String hash(String algorithm, String charset, String src) {
		try {
			return CodeKit.encodeHex(hash(algorithm, src.getBytes(charset)));
		} catch (Exception e) { throw new RuntimeException(e); }
	}
	
	/**
	 * HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
	 */
	public static byte[] hmac(String algorithm, byte[] key, byte[] src) 
			throws NoSuchAlgorithmException, InvalidKeyException {
		if(key == null || src == null || key.length == 0 || src.length == 0)
			throw new RuntimeException("src or key is null or blank!");
		SecretKey secretKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(src);
	}
	
	/**
	 * MD5, SHA-1, SHA-256, SHA-384, SHA-512
	 */
	public static byte[] hash(String algorithm, byte[] src) 
			throws NoSuchAlgorithmException {
		if(src == null || src.length == 0)
			throw new RuntimeException("src is null or blank!");
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return md.digest(src);
	}
}
