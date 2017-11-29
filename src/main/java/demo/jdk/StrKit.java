package demo.jdk;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StrKit {

    public static final String ENDL = System.getProperty("line.separator");

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                // case '\b':
                // case '\f':
                case ' ':
                case '\t':
                case '\n':
                case '\r': break;
                default: return false;
            }
        }
        return true;
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String toString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw); return sw.toString();
    }

    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if(firstChar >= 65 && firstChar <= 90) {
            char[] arr = str.toCharArray();
            arr[0] = (char) (arr[0] + 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if(firstChar >= 97 && firstChar <= 122) {
            char[] arr = str.toCharArray();
            arr[0] = (char) (arr[0] - 32);
            return new String(arr);
        } else {
            return str;
        }
    }

}
