package demo.encrypt.hmac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

import kit4j.HashKit;

public class DoHmac {
	
//	HmacMD5	128	BouncyCastle实现
//	HmacSHA1	160	BouncyCastle实现
//	HmacSHA256	256	BouncyCastle实现
//	HmacSHA384	384	BouncyCastle实现
//	HmacSHA512	512	JAVA6实现
//	HmacMD2	128	BouncyCastle实现
//	HmacMD4	128	BouncyCastle实现
//	HmacSHA224	224	BouncyCastle实现

	@Test
	public void test(){
		System.out.println(hmac("HmacMD5", "src", "key"));
		System.out.println(HashKit.hmd5("src", "key"));
		System.out.println(hmac("HmacSHA1", "src", "key"));
		System.out.println(hmac("HmacSHA256", "src", "key"));
		System.out.println(hmac("HmacSHA384", "src", "key"));
		System.out.println(hmac("HmacSHA512", "src", "key"));
//		System.out.println(hmac("HmacMD2", "src", "key"));
//		System.out.println(hmac("HmacMD4", "src", "key"));
//		System.out.println(hmac("HmacSHA224", "src", "key"));
	}
	
	/**
	 * hmac加密
	 * @param data 被加密的字符串
	 * @param key 密钥
	 * @param algorithm "HmacSHA256"、"HmacMD5"...
	 * @return 加密后的字符串
	 */
	public static String hmac(String algorithm, String src, String key) {
		try {
			return toHex(hmac(algorithm, 
							src.getBytes(), 
							key.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public static byte[] hmac(String algorithm, byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKey secretKey=new SecretKeySpec(key,algorithm);  
        Mac mac=Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);  
        return mac.doFinal(data);
	}
	
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	private static String toHex(byte[] bytes) {
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
}
