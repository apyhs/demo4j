package com.github.kahlkn.demo.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class DESedeDemo {
    private static final String DESEDE_ALGORITHM_NAME = "DESede";
    private static final String DESEDE_ECB_PKCS5PADDING = "DESede/ECB/PKCS5Padding";
    private static final String DESEDE_CBC_PKCS5PADDING = "DESede/CBC/PKCS5Padding";

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

}
