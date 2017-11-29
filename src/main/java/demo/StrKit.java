package demo;

import java.security.SecureRandom;
import java.util.*;

public class StrKit {
	private static final SecureRandom random = new SecureRandom();
	
	/**
	 * line separator
	 */
	public static final String LSEP = System.getProperty("line.separator");
	
	/**
	 * Change first char to lower case
	 */
	public static String firstChToLower(String s) {
		char firstChar = s.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = s.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return s;
	}
	
	/**
	 * Change first char to upper case
	 */
	public static String firstChToUpper(String s) {
		char firstChar = s.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = s.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return s;
	}
	
	/**
	 * String who is blank
	 */
	public static boolean isBlank(String s) {
		return !notBlank(s);
	}
	
	/**
	 * String who is not blank
	 */
	public static boolean notBlank(String s) {
		return s != null && s.trim().length() > 0;
	}
	
	/**
	 * judge s1 equals s2
	 */
	public static boolean equals(String s1, String s2) {
		 if(s1 != null) { return s1.equals(s2); }
		 else if(s2 == null) { return true; }
		 else{ return false; }
	}

	public static List<String> toList(String s) {
		List<String> result = new ArrayList<>();
		char[] chars = s.toCharArray();
		for (char aChar : chars)
			result.add(aChar + "");
		return result;
	}

	public static List<String> list(String... args) {
		List<String> result = new ArrayList<>();
		for (String arg : args) result.add(arg);
		return result;
	}

	public static List<List<String>> list(List<String>... args) {
		List<List<String>> result = new ArrayList<>();
		for (List<String> arg : args) result.add(arg);
		return result;
	}
	
	public static void removeRepeat(List<String> list){
		List<String> result = new ArrayList<>();
		for (String s : list)
			if(!result.contains(s)) result.add(s);
		list.clear(); list.addAll(result);
	}

	public static String removeRepeat(String s) {
		StringBuffer buffer = new StringBuffer();
		char[] array = s.toCharArray();
		for (char c : array) {
			if(buffer.indexOf(c + "") == -1) buffer.append(c);
		} return buffer.toString();
	}

	public static String randomSort(String s) {
		StringBuffer result = new StringBuffer();
		char[] array = s.toCharArray();
		for (int i = array.length - 1; i > 0; --i) {
			int nextInt = random.nextInt(i);
			result.append(array[nextInt]);
			char tmp = array[nextInt];
			array[nextInt] = array[i];
			array[i] = tmp;
		} result.append(array[0]);
		return result.toString();
	}

	public static void dictSort(List<String> list){
		Collections.sort(list);
	}

	public static void dictSort(String[] data){
		Arrays.sort(data);
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

}

