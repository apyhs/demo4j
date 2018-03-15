package com.github.kahlkn.demo.crypto;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class BlowfishDeno {

	@Test
	public void t1() throws Exception {
		String data = "hello,world!";
		String key = "sdvhujbasoindfca";
		String iv = "ashklfklafhbjshdjlkoiw".substring(0, 8);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv.getBytes()));

		outputStream.write(iv.getBytes());
		CipherOutputStream cipherStream = new CipherOutputStream(outputStream, cipher);
		for (int i = inputStream.read(); i != -1; i = inputStream.read()) {
			cipherStream.write(i);
		}
		inputStream.close();
		cipherStream.close();
		outputStream.close();

		System.out.println(Base64.encodeBase64String(outputStream.toByteArray()));
	}

	@Test
	public void t2() throws Exception {
		String data = "hello,world!";
		String key = "sdvhujbasoindfca";

		ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());

		CipherOutputStream cipherStream = new CipherOutputStream(outputStream, cipher);
		for (int i = inputStream.read(); i != -1; i = inputStream.read()) {
			cipherStream.write(i);
		}
		inputStream.close();
		cipherStream.close();
		outputStream.close();

		System.out.println(Base64.encodeBase64String(outputStream.toByteArray()));
	}

	@Test
	public void t3() throws Exception {
		String data = "hello,world!";
		String key = "sdvhuhgasoindfca";

		Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		System.out.println(Base64.encodeBase64String(cipher.doFinal(data.getBytes())));
	}

	// -------------------------------------------------
	public static final Integer BLOWFISH_IVLENGTH = 8;
	public static final Integer BLOWFISH_KEYSIZE = 128;
	public static final String BLOWFISH_ALGORITHM = "Blowfish";
	public static final String BLOWFISH_CBC_PKCS5PADDING = "Blowfish/CBC/PKCS5Padding";
	private Key key;

	public Key initialKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(BLOWFISH_ALGORITHM);
			keyGenerator.init(BLOWFISH_KEYSIZE.intValue());
			key = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return key;
	}

	public byte[] encrypt(byte[] plainBytes) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(plainBytes);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			SecureRandom random = new SecureRandom();
			byte[] iv = new byte[BLOWFISH_IVLENGTH];
			random.nextBytes(iv);
			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, iv);
			outputStream.write(iv);
			CipherOutputStream cipherStream = new CipherOutputStream(outputStream, cipher);
			for (int i = inputStream.read(); i != -1; i = inputStream.read()) {
				cipherStream.write(i);
			}
			inputStream.close();
			cipherStream.close();
			outputStream.close();

			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public byte[] decrypt(byte[] cipherBytes) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(cipherBytes);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] iv = new byte[BLOWFISH_IVLENGTH];
			inputStream.read(iv);
			Cipher cipher = getCipher(Cipher.DECRYPT_MODE, iv);
			CipherInputStream cipherStream = new CipherInputStream(inputStream, cipher);
			for (int i = cipherStream.read(); i != -1; i = cipherStream.read()) {
				outputStream.write(i);
			}
			inputStream.close();
			cipherStream.close();
			outputStream.close();

			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Cipher getCipher(int mode, byte[] iv) {
		IvParameterSpec spec = new IvParameterSpec(iv);
		try {
			Cipher cipher = Cipher.getInstance(BLOWFISH_CBC_PKCS5PADDING);
			SecretKeySpec secretKey = new SecretKeySpec("123456789".getBytes(), BLOWFISH_ALGORITHM);
//			cipher.init(mode, key, spec);
			cipher.init(mode, secretKey, spec);

			return cipher;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
