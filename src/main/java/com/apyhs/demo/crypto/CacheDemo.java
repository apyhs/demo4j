package com.apyhs.demo.crypto;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;

public class CacheDemo {

    public void testaaaa() {
        Security.getProviders();
        new sun.security.provider.Sun(); // security.provider.1
        new sun.security.rsa.SunRsaSign(); // security.provider.2
        new com.sun.net.ssl.internal.ssl.Provider(); // security.provider.3
        new com.sun.crypto.provider.SunJCE(); // security.provider.4
        new sun.security.jgss.SunProvider(); // security.provider.5
        new com.sun.security.sasl.Provider(); // security.provider.6
        new BouncyCastleProvider(); // 轻量级密码术包
    }

    @Test
    public void test1() throws Exception {
        byte[] key = "123".getBytes();
        byte[] data = "123hello".getBytes();
        String algorithm = "SslMacSHA1";
        SecretKey secretKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        System.out.println(Hex.encodeHexString(mac.doFinal(data)));
    }

    @Test
    public void test2() throws Exception {
        KeyGenerator sunTlsPrf = KeyGenerator.getInstance("SunTlsPrf");
        sunTlsPrf.init(new SecureRandom());
        System.out.println(sunTlsPrf.getProvider().getClass());
        System.out.println(sunTlsPrf.getAlgorithm());
        System.out.println(Hex.encodeHexString(sunTlsPrf.generateKey().getEncoded()));
    }

}
