package demo.encrypt.blowfish;

import kit4j.CodeKit;
import kit4j.EncrKit;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;

public class Demo {

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
		
		System.out.println(CodeKit.encodeBase64(outputStream.toByteArray()));
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
		
		System.out.println(CodeKit.encodeBase64(outputStream.toByteArray()));
	}
	
	@Test
	public void t3() throws Exception {
		String data = "hello,world!";
		String key = "sdvhuhgasoindfca";
		
		Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		System.out.println(CodeKit.encodeBase64(cipher.doFinal(data.getBytes())));
	}
	
	@Test
	public void t4() {
		String data = "hello,world!";
		String key = "sdvjdvxsdnqadfca";
		byte[] encrypt = EncrKit.blowfish(true, key.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeBase64(encrypt));
		byte[] decrypt = EncrKit.blowfish(false, key.getBytes(), encrypt);
		System.out.println(new String(decrypt));
	}
	
	@Test
	public void t5() {
		String data = "hello,world!";
		String key = "sdvjdvxsdnqadfca";
		String iv = "dvfesfhb";
		byte[] encrypt = EncrKit.blowfish(true, key.getBytes(), iv.getBytes(), data.getBytes());
		System.out.println(CodeKit.encodeBase64(encrypt));
		byte[] decrypt = EncrKit.blowfish(false, key.getBytes(), iv.getBytes(), encrypt);
		System.out.println(new String(decrypt));
	}
}
