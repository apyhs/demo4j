package demo;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * coding kit
 */
public class CodeKit {
    private static String defaultCharsetName = Charset.defaultCharset().name();

    public static void setDefCharsetName(String charset){
        if (StrKit.isBlank(charset)) {
            throw new IllegalArgumentException("charset name can not be blank!");
        } defaultCharsetName = charset;
    }

    //----
    public static String encodeUrl(String data) {
        return encodeUrl(data, defaultCharsetName);
    }

    public static String encodeUrl(String data, String charset) {
        try { return URLEncoder.encode(data, charset); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String decodeUrl(String data) {
        return decodeUrl(data, defaultCharsetName);
    }

    public static String decodeUrl(String data, String charset) {
        try { return URLDecoder.decode(data, charset); }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    //----
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

    //----
    public static <K, V> String encodeMap(Map<K, V> map) {
        return encodeMap(map, "=", "&");
    }

    public static Map<String, String> decodeMap(String data){
        return decodeMap(data, "=", "&");
    }

    public static <K, V> String encodeMap(Map<K, V> map, String keySeparator, String valueSeparator) {
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<K, V>> entries = map.entrySet();
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

    //----
}
