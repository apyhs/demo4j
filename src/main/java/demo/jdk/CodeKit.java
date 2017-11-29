package demo.jdk;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodeKit {

    public static String encodeUnicode(String string) {
        StringBuilder unicode = new StringBuilder();
        char[] chars = string.toCharArray();
        for (char c : chars)
            unicode.append("\\u" + Integer.toHexString(c));
        return unicode.toString();
    }

    public static String decodeUnicode(String unicode) {
        StringBuilder result = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++)
            result.append((char)Integer.parseInt(hex[i], 16));
        return result.toString();
    }

    public static String encodeUrl(String data, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(data, charset);
    }

    public static String decodeUrl(String data, String charset) throws UnsupportedEncodingException {
        return URLDecoder.decode(data, charset);
    }

    public static String encodeMap(Map<?, ?> map) {
        return encodeMap(map, "=", "&");
    }

    public static String encodeMap(Map<?, ?> map, String keySeparator, String valueSeparator) {
        StringBuilder builder = new StringBuilder();
        Set<? extends Map.Entry<?, ?>> entries = map.entrySet();
        if (entries.size() > 0) {
            for (Map.Entry entry : entries) {
                builder.append(entry.getKey());
                builder.append(keySeparator);
                builder.append(entry.getValue());
                builder.append(valueSeparator);
            } int len = builder.length();
            int valLen = valueSeparator.length();
            builder.delete(len - valLen, len);
        } return builder.toString();
    }

    public static Map<String, String> decodeMap(String data) {
        return decodeMap(data, "=", "&");
    }

    public static Map<String, String> decodeMap(String data, String keySeparator, String valueSeparator) {
        Map<String, String> result = new HashMap<>();
        String[] split = data.split(valueSeparator);
        if (split.length > 0) {
            for (String s : split) {
                String[] entry = s.split(keySeparator);
                if (entry.length == 2) {
                    result.put(entry[0], entry[1]);
                }
            }
        } return result;
    }

}
