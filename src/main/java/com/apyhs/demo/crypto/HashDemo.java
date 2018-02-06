package com.apyhs.demo.crypto;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;

public class HashDemo {

	@Test
	public void test1() {
		String input = "12345678";
		System.out.println(hash(input, "MD5"));
		System.out.println(hash(input, "SHA-1"));
		System.out.println(hash(input, "SHA-256"));
		System.out.println(hash(input, "SHA-384"));
		System.out.println(hash(input, "SHA-512"));
	}

	/**
	 * MD5, SHA-1, SHA-256, SHA-384, SHA-512
	 */
	public static String hash(String data, String algorithm) {
		try {
			byte[] dataArr = data.getBytes();
			if(dataArr == null || dataArr.length == 0)
				throw new IllegalArgumentException("dataArr is null or blank!");
			MessageDigest md = MessageDigest.getInstance(algorithm);
			return new String(new Hex().encode(md.digest(dataArr)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
