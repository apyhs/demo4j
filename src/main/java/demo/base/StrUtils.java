package demo.base;

public class StrUtils {

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

    public static boolean isNotBlank(String str) {
        return !StrUtils.isBlank(str);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String convertCamelCase(String stringWithUnderline) {
        if (stringWithUnderline.indexOf('_') == -1) {
            return stringWithUnderline;
        }
        stringWithUnderline = stringWithUnderline.toLowerCase();
        char[] from = stringWithUnderline.toCharArray();
        char[] result = new char[from.length];
        int j = 0;
        for (int i=0; i<from.length; i++) {
            if (from[i] == '_') {
                i++;
                if (i < from.length) {
                    result[j++] = Character.toUpperCase(from[i]);
                }
            } else {
                result[j++] = from[i];
            }
        }
        return new String(result, 0, j);
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
