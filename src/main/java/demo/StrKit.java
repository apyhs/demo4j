package demo;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * string kit
 */
public class StrKit {
	public static final String LSEP = System.getProperty("line.separator");

	public static String firstChToLower(String s) {
		char firstChar = s.charAt(0);
		if(firstChar >= 65 && firstChar <= 90) {
			char[] arr = s.toCharArray();
			arr[0] = (char)(arr[0] + 32);
			return new String(arr);
		} else { return s; }
	}

	public static String firstChToUpper(String s) {
		char firstChar = s.charAt(0);
		if(firstChar >= 97 && firstChar <= 122) {
			char[] arr = s.toCharArray();
			arr[0] = (char)(arr[0] - 32);
			return new String(arr);
		} else { return s; }
	}

	public static boolean isBlank(String s) {
		if (s == null) {
			return true;
		}
		int len = s.length();
		if (len == 0) {
			return true;
		}
		for (int i = 0; i < len; i++) {
			switch (s.charAt(i)) {
				case ' ':
				case '\t':
				case '\n':
				case '\r':
				// case '\b':
				// case '\f':
					break;
				default:
					return false;
			}
		}
		return true;
	}

	public static boolean notBlank(String s) {
		return !isBlank(s);
	}

	public static boolean equals(String s1, String s2) {
		return s1 == null ? s2 == null : s1.equals(s2);
	}

	public static String toString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw); return sw.toString();
	}

	public static boolean isRepeat(String s) {
		int len = s.length();
		for (int i = 0; i < len; i++) {
			if (s.indexOf(s.charAt(i), i + 1) > 0)
				return true;
		} return false;
	}

	public static String removeRepeat(String s) {
		StringBuilder result = new StringBuilder();
		char[] array = s.toCharArray();
		for (char c : array) {
			if(result.indexOf(c + "") == -1)
				result.append(c);
		} return result.toString();
	}

}

