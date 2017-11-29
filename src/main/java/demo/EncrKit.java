package demo;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * encrypt kit
 */
public class EncrKit {

    //----
    private static String defaultCharsetName = Charset.defaultCharset().name();
    private static final char[] HEX_DIGITS = "0123456789abcdef".toCharArray();

    private static String toHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for(int i = 0; i < bytes.length; ++i) {
            ret.append(HEX_DIGITS[bytes[i] >> 4 & 15]);
            ret.append(HEX_DIGITS[bytes[i] & 15]);
        }
        return ret.toString();
    }

    public static void setDefCharsetName(String charset){
        if (StrKit.isBlank(charset)) {
            throw new IllegalArgumentException("charset name can not be blank!");
        } defaultCharsetName = charset;
    }

    //----
    private static final String HASH_ALGORITHM_MD5 = "MD5";
    private static final String HASH_ALGORITHM_SHA1 = "SHA-1";
    private static final String HASH_ALGORITHM_SHA256 = "SHA-256";
    private static final String HASH_ALGORITHM_SHA384 = "SHA-384";
    private static final String HASH_ALGORITHM_SHA512 = "SHA-512";

    public static String md5(String data) {
        return md5(data, defaultCharsetName);
    }

    public static String md5(String data, String charset) {
        try { return toHex(md5(data.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] md5(byte[] data) {
        try { return hash(data, HASH_ALGORITHM_MD5); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String sha1(String data) {
        return sha1(data, defaultCharsetName);
    }

    public static String sha1(String data, String charset) {
        try { return toHex(sha1(data.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] sha1(byte[] data) {
        try { return hash(data, HASH_ALGORITHM_SHA1); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String sha256(String data) {
        return sha256(data, defaultCharsetName);
    }

    public static String sha256(String data, String charset) {
        try { return toHex(sha256(data.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] sha256(byte[] data) {
        try { return hash(data, HASH_ALGORITHM_SHA256); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String sha384(String data) {
        return sha384(data, defaultCharsetName);
    }

    public static String sha384(String data, String charset) {
        try { return toHex(sha384(data.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] sha384(byte[] data) {
        try { return hash(data, HASH_ALGORITHM_SHA384); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String sha512(String data){
        return sha512(data, defaultCharsetName);
    }

    public static String sha512(String data, String charset) {
        try { return toHex(sha512(data.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] sha512(byte[] data) {
        try { return hash(data, HASH_ALGORITHM_SHA512); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    /**
     * MD5, SHA-1, SHA-256, SHA-384, SHA-512
     */
    public static byte[] hash(byte[] data, String algorithm) throws NoSuchAlgorithmException {
        if(data == null || data.length == 0) throw new IllegalArgumentException("data is null or blank!");
        MessageDigest md = MessageDigest.getInstance(algorithm); return md.digest(data);
    }

    //----
    private static final String HMAC_ALGORITHM_HMACMD5 = "HmacMD5";
    private static final String HMAC_ALGORITHM_HMACSHA1 = "HmacSHA1";
    private static final String HMAC_ALGORITHM_HMACSHA256= "HmacSHA256";
    private static final String HMAC_ALGORITHM_HMACSHA384 = "HmacSHA384";
    private static final String HMAC_ALGORITHM_HMACSHA512 = "HmacSHA512";

    public static String hmd5(String data, String key) {
        return hmd5(data, key, defaultCharsetName);
    }

    public static String hmd5(String data, String key, String charset) {
        try { return toHex(hmd5(data.getBytes(charset), key.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] hmd5(byte[] data, byte[] key) {
        try { return hmac(data, key, HMAC_ALGORITHM_HMACMD5); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String hsha1(String data, String key) {
        return hsha1(data, key, defaultCharsetName);
    }

    public static String hsha1(String data, String key, String charset) {
        try { return toHex(hsha1(data.getBytes(charset), key.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] hsha1(byte[] data, byte[] key) {
        try { return hmac(data, key, HMAC_ALGORITHM_HMACSHA1); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String hsha256(String data, String key) {
        return hsha256(data, key, defaultCharsetName);
    }

    public static String hsha256(String data, String key, String charset) {
        try { return toHex(hsha256(data.getBytes(charset), key.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] hsha256(byte[] data, byte[] key) {
        try { return hmac(data, key, HMAC_ALGORITHM_HMACSHA256); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String hsha384(String data, String key) {
        return hsha384(data, key, defaultCharsetName);
    }

    public static String hsha384(String data, String key, String charset) {
        try { return toHex(hsha384(data.getBytes(charset), key.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] hsha384(byte[] data, byte[] key) {
        try { return hmac(data, key, HMAC_ALGORITHM_HMACSHA384); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String hsha512(String data, String key) {
        return hsha512(data, key, defaultCharsetName);
    }

    public static String hsha512(String data, String key, String charset) {
        try { return toHex(hsha512(data.getBytes(charset), key.getBytes(charset))); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] hsha512(byte[] data, byte[] key) {
        try { return hmac(data, key, HMAC_ALGORITHM_HMACSHA512); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    /**
     * HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512
     */
    public static byte[] hmac(byte[] data, byte[] key, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException {
        if(key == null || data == null || key.length == 0 || data.length == 0)
            throw new IllegalArgumentException("data or key is null or blank!");
        SecretKey secretKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey); return mac.doFinal(data);
    }

    //----
    private static final String DES_ALGORITHM_NAME = "DES";
    private static final String DES_ECB_PKCS5PADDING = "DES/ECB/PKCS5Padding";
    private static final String DES_CBC_PKCS5PADDING = "DES/CBC/PKCS5Padding";

    public static byte[] desEcbPkcs5Encrypt(byte[] data, byte[] key) {
        return desEcbPkcs5(data, key, true);
    }

    public static byte[] desEcbPkcs5Decrypt(byte[] data, byte[] key) {
        return desEcbPkcs5(data, key, false);
    }

    public static byte[] desEcbPkcs5(byte[] data, byte[] key, boolean type) {
        try {
            if(key.length != 8) throw new IllegalArgumentException("key length must is 8!");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM_NAME);
            SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
            Cipher cipher = Cipher.getInstance(DES_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] desCbcPkcs5Encrypt(byte[] data, byte[] key, byte[] iv) {
        return desCbcPkcs5(data, key, iv, true);
    }

    public static byte[] desCbcPkcs5Decrypt(byte[] data, byte[] key, byte[] iv) {
        return desCbcPkcs5(data, key, iv, false);
    }

    public static byte[] desCbcPkcs5(byte[] data, byte[] key, byte[] iv, boolean type) {
        try {
            if(key.length != 8) throw new IllegalArgumentException("key length must is 8!");
            if(iv.length != 8) throw new IllegalArgumentException("iv length must is 8!");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM_NAME);
            SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    //----
    private static final String DESEDE_ALGORITHM_NAME = "DESede";
    private static final String DESEDE_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
    private static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";

    public static byte[] desedeEcbPkcs5Encrypt(byte[] data, byte[] key) {
        return desedeEcbPkcs5(data, key, true);
    }

    public static byte[] desedeEcbPkcs5Decrypt(byte[] data, byte[] key) {
        return desedeEcbPkcs5(data, key, false);
    }

    public static byte[] desedeEcbPkcs5(byte[] data, byte[] key, boolean type) {
        try {
            if(key.length != 24) throw new IllegalArgumentException("key length must is 24!");
            SecretKey secretKey = new SecretKeySpec(key, DESEDE_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(DESEDE_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] desedeCbcPkcs5Encrypt(byte[] data, byte[] key, byte[] iv) {
        return desedeCbcPkcs5(data, key, iv, true);
    }

    public static byte[] desedeCbcPkcs5Decrypt(byte[] data, byte[] key, byte[] iv) {
        return desedeCbcPkcs5(data, key, iv, false);
    }

    public static byte[] desedeCbcPkcs5(byte[] data, byte[] key, byte[] iv, boolean type) {
        try {
            if(key.length != 24) throw new IllegalArgumentException("key length must is 24!");
            if(iv.length != 8) throw new IllegalArgumentException("iv length must is 8!");
            SecretKey secretKey = new SecretKeySpec(key, DESEDE_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(DESEDE_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    //----
    private static final String AES_ALGORITHM_NAME = "AES";
    private static final String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
    private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

    public static byte[] aesEcbNoPadEncrypt(byte[] data, byte[] key) {
        return aesEcbNoPad(data, key, true);
    }

    public static byte[] aesEcbNoPadDecrypt(byte[] data, byte[] key) {
        return aesEcbNoPad(data, key, false);
    }

    public static byte[] aesEcbNoPad(byte[] data, byte[] key, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            Cipher cipher = Cipher.getInstance(AES_ECB_NOPADDING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ALGORITHM_NAME);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            if (type) {
                int len = data.length; // Input length multiple of 16 bytes
                byte[] finalData = new byte[len + 16 - len % 16];
                System.arraycopy(data, 0, finalData, 0, len);
                return cipher.doFinal(finalData);
            } else {
                return cipher.doFinal(data);
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static byte[] aesEcbPkcs5Encrypt(byte[] data, byte[] key) {
        return aesEcbPkcs5(data, key, true);
    }

    public static byte[] aesEcbPkcs5Decrypt(byte[] data, byte[] key) {
        return aesEcbPkcs5(data, key, false);
    }

    public static byte[] aesEcbPkcs5(byte[] data, byte[] key, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ALGORITHM_NAME);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] aesCbcPkcs5Encrypt(byte[] data, byte[] key, byte[] iv) {
        return aesCbcPkcs5(data, key, iv, true);
    }

    public static byte[] aesCbcPkcs5Decrypt(byte[] data, byte[] key, byte[] iv) {
        return aesCbcPkcs5(data, key, iv, false);
    }

    public static byte[] aesCbcPkcs5(byte[] data, byte[] key, byte[] iv, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            if(iv.length != 16) throw new IllegalArgumentException("iv length must is 16!");
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            SecretKeySpec secretKey = new SecretKeySpec(key, AES_ALGORITHM_NAME);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    //----
    private static final String BLOWFISH_ALGORITHM_NAME = "Blowfish";
    private static final String BLOWFISH_ECB_PKCS5PADDING = "Blowfish/ECB/PKCS5Padding";
    private static final String BLOWFISH_CBC_NOPADDING = "Blowfish/CBC/NoPadding";
    private static final String BLOWFISH_CBC_PKCS5PADDING = "Blowfish/CBC/PKCS5Padding";

    public static byte[] blowfishCbcNoPadEncrypt(byte[] data, byte[] key, byte[] iv) {
        return blowfishCbcNoPad(data, key, iv, true);
    }

    public static byte[] blowfishCbcNoPadDecrypt(byte[] data, byte[] key, byte[] iv) {
        return blowfishCbcNoPad(data, key, iv, false);
    }

    public static byte[] blowfishCbcNoPad(byte[] data, byte[] key, byte[] iv, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            if(iv.length != 8) throw new IllegalArgumentException("iv length must is 8!");
            SecretKey secretKey = new SecretKeySpec(key, BLOWFISH_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(BLOWFISH_CBC_NOPADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            if (type) {
                int len = data.length; // Input length multiple of 8 bytes
                byte[] finalData = new byte[len + 8 - len % 8];
                System.arraycopy(data, 0, finalData, 0, len);
                return cipher.doFinal(finalData);
            } else {
                return cipher.doFinal(data);
            }
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] blowfishEcbPkcs5Encrypt(byte[] data, byte[] key) {
        return blowfishEcbPkcs5(data, key, true);
    }

    public static byte[] blowfishEcbPkcs5Decrypt(byte[] data, byte[] key) {
        return blowfishEcbPkcs5(data, key, false);
    }

    public static byte[] blowfishEcbPkcs5(byte[] data, byte[] key, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            SecretKey secretKey = new SecretKeySpec(key, BLOWFISH_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(BLOWFISH_ECB_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new SecureRandom());
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] blowfishCbcPkcs5Encrypt(byte[] data, byte[] key, byte[] iv) {
        return blowfishCbcPkcs5(data, key, iv, true);
    }

    public static byte[] blowfishCbcPkcs5Decrypt(byte[] data, byte[] key, byte[] iv) {
        return blowfishCbcPkcs5(data, key, iv, false);
    }

    public static byte[] blowfishCbcPkcs5(byte[] data, byte[] key, byte[] iv, boolean type) {
        try {
            if(key.length != 16) throw new IllegalArgumentException("key length must is 16!");
            if(iv.length != 8) throw new IllegalArgumentException("iv length must is 8!");
            SecretKey secretKey = new SecretKeySpec(key, BLOWFISH_ALGORITHM_NAME);
            Cipher cipher = Cipher.getInstance(BLOWFISH_CBC_PKCS5PADDING);
            int opmode = type ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
            cipher.init(opmode, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(data);
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    //----
}
