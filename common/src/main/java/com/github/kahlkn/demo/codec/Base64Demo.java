package com.github.kahlkn.demo.codec;

import java.lang.reflect.Method;

public class Base64Demo {

    public static String encodeBase64(byte[] input) {
        // 以反射的方式 调用JDK中 rt.jar 的工具类
        try {
            Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method encode = clazz.getMethod("encode", byte[].class);
            encode.setAccessible(true);
            Object result = encode.invoke(null, new Object[]{input});
            return (String) result;
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static byte[] decodeBase64(String input) {
        // 以反射的方式 调用JDK中 rt.jar 的工具类
        try {
            Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method decode = clazz.getMethod("decode", String.class);
            decode.setAccessible(true);
            Object result = decode.invoke(null, input);
            return (byte[]) result;
        }catch (Exception e){ throw new RuntimeException(e); }
    }

    public static String encodeBase64_1(byte[] data) {
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data);
    }

    public static byte[] decodeBase64_1(String data) {
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(data);
    }

}
