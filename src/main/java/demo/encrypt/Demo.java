package demo.encrypt;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import demo.encrypt.blowfish.BlowfishAlgorithmEncryptor;
import demo.encrypt.blowfish.BlowfishECB;
import kit4j.CodeKit;
import kit4j.EncrKit;
import org.junit.Test;

public class Demo {
	
	@Test
	public void t1(){
		byte[] data = {-91, -45, 124, 61, -123, 2, -43, 3, -52, 123, 30, -74, 44, -17, 75, 121};
		String data1 = "A5D37C3D8502D503CC7B1EB62CEF4B79";
		System.out.println(CodeKit.encodeHex(data));
		System.out.println(Arrays.toString(CodeKit.decodeHex(data1)));
	}
	
	@Test
	public void t4(){
		String data = "你好，世界！";
//		String key = "2C71234561B6C29D";
		String key = "kfwsedsg";
		byte[] encrypt = EncrKit.des(true, key.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = EncrKit.des(false, key.getBytes(), CodeKit.decodeBase64("W7V84zeevKbYB2S3benAqMZVVgyQVg4f"));
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t5() throws Exception{
		String data = "hello,World!";
//		String key = "123456789";
		String key = "1288fyh89tjda456";
		byte[] encrypt = EncrKit.aes(true, key.getBytes("utf-8"), data.getBytes("utf-8"));
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = EncrKit.aes(false, key.getBytes(), CodeKit.decodeBase64("+9co05HMKKXeD+cdc4kO6g=="));
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t6() throws Exception{
		String data = "hello,World!";
		String key = "1288fyh89tjda456";
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skey
, new IvParameterSpec(new byte[16])
);
        byte[] encrypt = cipher.doFinal(data.getBytes());
        System.out.println(CodeKit.encodeHex(encrypt));
	}
	
	@Test
	public void t7() throws Exception{
		String data = "hello,World!";
		String key = "1288fyh89tjda456";
//		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(128, new SecureRandom(key.getBytes()));
		SecretKey skey = generator.generateKey();
		
//      SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skey);
        byte[] encrypt = cipher.doFinal(data.getBytes());
        System.out.println(CodeKit.encodeHex(encrypt));
	}
	
	@Test
	public void t8() throws Exception{
		String data = "hello,World!";
		String key = "1288fyh89tjdasdf";
		String iv = "1234567890123456";
		System.out.println(CodeKit.encodeHex(EncrKit.aes(true, key.getBytes(), iv.getBytes(), data.getBytes())));
		byte[] decrypt = EncrKit.aes(false, key.getBytes(), iv.getBytes(), CodeKit.decodeBase64("fLw+c+p+qk/1WHofhqLLag=="));
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t9() throws Exception{
		String data = "hello,World!";
//		String key = "1288fyh8";
		String key = "1288fyh81288fyh81288fyh8";
		String iv = "12345678";
//		byte[] handle = handle(Cipher.ENCRYPT_MODE, key.getBytes(), new byte[8], data.getBytes());
		byte[] handle = handle(Cipher.ENCRYPT_MODE, key.getBytes(), iv.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeHex(handle));
	}
	
	private static byte[] handle(int opmode, byte[] key, byte[] iv, byte[] data) 
			throws GeneralSecurityException {
//		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//		SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
		SecretKey secretKey = new SecretKeySpec(key, "DESede");  
//		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
//		cipher.init(opmode, secretKey, new IvParameterSpec(iv));
		cipher.init(opmode, secretKey, new SecureRandom());
		return cipher.doFinal(data);
	}
	
	@Test
	public void t10() throws Exception{
		String data = "hello,World!";
		String key = "thhgfyh8";
		String iv = "sdgsxnsc";
		byte[] encrypt = EncrKit.des(true, key.getBytes(), iv.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = EncrKit.des(false, key.getBytes(), iv.getBytes(), encrypt);
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t11(){
		String data = "hello,World!";
		String key = "bhegfyh81sd8fyh8hde8fyh8";
		byte[] encrypt = EncrKit.desede(true, key.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = EncrKit.desede(false, key.getBytes(), encrypt);
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t12(){
		String data = "hello,World!";
		String key = "bhegfyh81sd8fyh8hde8fyh8";
		String iv = "sdgsxnsc";
		byte[] encrypt = EncrKit.desede(true, key.getBytes(), iv.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = EncrKit.desede(false, key.getBytes(), iv.getBytes(), encrypt);
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t13(){
		// ECB/zeropadding
		String data = "hello,World!";
//		String key = "E0FEE0FEF1FEF1FE";
		String key = "afasfkjhsdjsdggggggggggggggggggggrtujrjjjjjjjjjjjjjjjjjjjjhash1afasfkjhsdjfhash";
		key = key.substring(0, 56);
		BlowfishECB bf = new BlowfishECB(key.getBytes());
		byte[] encrypt = bf.encrypt(data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
		byte[] decrypt = bf.decrypt(encrypt);
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t14(){
		String data = "hello,World!";
		String key = "afasfkjhsdjsdggggggggggggggggggggrtujrjjjjjjjjjjjjjjjjjjjjhash1afasfkjhsdjfhash";
		key = key.substring(0, 56);
		BlowfishAlgorithmEncryptor bf = new BlowfishAlgorithmEncryptor();
		byte[] encrypt = bf.encrypt(data.getBytes());
		System.out.println(CodeKit.encodeHex(encrypt));
//		byte[] decrypt = bf.decrypt(encrypt);
//		System.out.println(new String(decrypt));
	}
	
}
