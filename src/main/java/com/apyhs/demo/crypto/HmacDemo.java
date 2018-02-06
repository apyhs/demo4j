package com.apyhs.demo.crypto;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacDemo {
	
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
		System.out.println(hmac("src", "key", "HmacMD5"));
		System.out.println(hmac("src", "key", "HmacSHA1"));
		System.out.println(hmac("src", "key", "HmacSHA256"));
		System.out.println(hmac("src", "key", "HmacSHA384"));
		System.out.println(hmac("src", "key", "HmacSHA512"));
//		System.out.println(hmac("src", "key", "HmacMD2"));
//		System.out.println(hmac("src", "key", "HmacMD4"));
//		System.out.println(hmac("src", "key", "HmacSHA224"));
	}

	/**
	 * HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
	 */
	public static String hmac(String data, String key, String algorithm) {
		try {
			byte[] dataArr = data.getBytes();
			byte[] keyArr = key.getBytes();
			if(keyArr.length == 0 || dataArr.length == 0)
				throw new IllegalArgumentException("dataArr or key is blank!");
			SecretKey secretKey = new SecretKeySpec(keyArr, algorithm);
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			return new String(new Hex().encode(mac.doFinal(dataArr)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
