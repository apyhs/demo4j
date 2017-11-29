package demo;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * true = encrypt, false = decrypt<br />
 * Blowfish/ECB/PKCS5Padding<br />
 * Blowfish/CBC/PKCS5Padding<br />
 * AES/ECB/PKCS5Padding 128<br />
 * AES/CBC/PKCS5Padding 128<br />
 * DESede/ECB/PKCS5Padding< br/>
 * DESede/CBC/PKCS5Padding< br/>
 * DES/ECB/PKCS5Padding<br />
 * DES/CBC/PKCS5Padding<br />
 */
public class EncrKit {
    private static final SecureRandom random = new SecureRandom();
    private static final char[] CHAR_ARRAY = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * generate sequence
     */
    public static String generateSeq(int size) {
        return generateSeq(CHAR_ARRAY, size);
    }

    /**
     * generate sequence
     */
    public static String generateSeq(char[] chars, int size) {
        StringBuffer result = new StringBuffer();
        for (int i = 0, len = chars.length; i < size; i++)
            result.append(chars[random.nextInt(len)]);
        return result.toString();
    }

    private static final String BLOWFISH_ALGORITHM_NAME = "Blowfish";
    private static final String BLOWFISH_ECB_PKCS5PADDING = "Blowfish/ECB/PKCS5Padding";
    private static final String BLOWFISH_CBC_PKCS5PADDING = "Blowfish/CBC/PKCS5Padding";
    private static final String AES_ALGORITHM_NAME = "AES";
    private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    private static final String DESEDE_ALGORITHM_NAME = "DESede";
    private static final String DESEDE_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
    private static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";
    private static final String DES_ALGORITHM_NAME = "DES";
    private static final String DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
    private static final String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";

    public static byte[] blowfish(boolean type, byte[] key, byte[] iv, byte[] data) {
        try {
            if(key.length != 16) throw new RuntimeException("key length must is 16!");
            if(iv.length != 8) throw new RuntimeException("iv length must is 8!");
            SecretKey secretKey = new SecretKeySpec(key, BLOWFISH_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(BLOWFISH_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] blowfish(boolean type, byte[] key, byte[] data) {
        try {
            if(key.length != 16) throw new RuntimeException("key length must is 16!");
            SecretKey secretKey = new SecretKeySpec(key, BLOWFISH_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(BLOWFISH_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] aes(boolean type, byte[] key, byte[] iv, byte[] data) {
        try {
            if(key.length != 16) throw new RuntimeException("key length must is 16!");
            if(iv.length != 16) throw new RuntimeException("iv length must is 16!");
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ALGORITHM_NAME);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] aes(boolean type, byte[] key, byte[] data) {
        try {
            if(key.length != 16) throw new RuntimeException("key length must is 16!");
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ALGORITHM_NAME);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] desede(boolean type, byte[] key, byte[] iv, byte[] data) {
        try {
            if(key.length != 24) throw new RuntimeException("key length must is 24!");
            if(iv.length != 8) throw new RuntimeException("iv length must is 8!");
            SecretKey secretKey = new SecretKeySpec(key, DESEDE_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(DESEDE_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] desede(boolean type, byte[] key, byte[] data) {
        try {
            if(key.length != 24) throw new RuntimeException("key length must is 24!");
            SecretKey secretKey = new SecretKeySpec(key, DESEDE_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(DESEDE_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] des(boolean type, byte[] key, byte[] iv, byte[] data) {
        try {
            if(key.length != 8) throw new RuntimeException("key length must is 8!");
            if(iv.length != 8) throw new RuntimeException("iv length must is 8!");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM_NAME);
            SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] des(boolean type, byte[] key, byte[] data) {
        try {
            if(key.length != 8) throw new RuntimeException("key length must is 8!");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM_NAME);
            SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
            Cipher cipher = Cipher.getInstance(DES_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){  throw new RuntimeException(e); }
    }
}
