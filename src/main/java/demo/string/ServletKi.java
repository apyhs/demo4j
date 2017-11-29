package demo.string;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletKi {
	
	public static String LINESEPARATOR = System.getProperty("line.separator");
	
	private static final String BRACKETS_LEFT = "[";
	private static final String BRACKETS_RIGHT = "]";
	private static final String BLANK_SPACE = " ";
	private static final String ENGLISH_COLON = ":";
	private static final String ENGLISH_COMMA = ",";
	private static final String CHARACTER_EQUAL = "=";
	private static final String BRACES_LEFT = "{";
	private static final String BRACES_RIGHT = "}";
	private static final String CHARACTER_TO = "To";
	private static final String CHARACTER_ENCODE = "Encode";
	private static final String CHARACTER_CONTENTTYPE = "ContentType";
	
	public static String toString(HttpServletRequest request){
		StringBuffer strb = new StringBuffer().append(BRACKETS_LEFT).append(request.getRemoteAddr()).append(ENGLISH_COLON)
				.append(request.getRemotePort()).append(BRACKETS_RIGHT).append(BLANK_SPACE).append(request.getMethod()).append(BLANK_SPACE);
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<String> keys = parameterMap.keySet();
		if(keys.size() != 0){
			strb.append(BRACKETS_LEFT);
			for (String key : keys) {
				String[] values = parameterMap.get(key);
				if(values.length == 1){
					strb.append(key).append(CHARACTER_EQUAL).append(values[0]).append(ENGLISH_COMMA);
				} else {
					strb.append(key).append(BRACKETS_LEFT).append(BRACKETS_RIGHT).append(CHARACTER_EQUAL).append(BRACES_LEFT);
					for (String value : values) {
						strb.append(value).append(ENGLISH_COMMA);
					}
					strb.deleteCharAt(strb.length() - 1).append(BRACES_RIGHT).append(ENGLISH_COMMA);
				}
			}
			strb.deleteCharAt(strb.length() - 1).append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			strb.append(Arrays.toString(request.getCookies())).append(BLANK_SPACE);
		}
		
		strb.append(CHARACTER_TO).append(BLANK_SPACE).append(BRACKETS_LEFT).append(request.getRequestURL().toString())
				.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		
		String characterEncoding = request.getCharacterEncoding();
		if(characterEncoding != null && characterEncoding.trim().length() > 0){
			strb.append(CHARACTER_ENCODE).append(BLANK_SPACE).append(BRACKETS_LEFT).append(characterEncoding)
					.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}
		
		String contentType = request.getContentType();
		if(contentType != null && contentType.trim().length() > 0){
			strb.append(CHARACTER_CONTENTTYPE).append(BLANK_SPACE).append(BRACKETS_LEFT).append(contentType)
					.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}
		
		return strb.append(LINESEPARATOR).toString();
	}
	
	public static String getRequestHeaders(HttpServletRequest request){
		StringBuffer result = new StringBuffer();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = (String) headerNames.nextElement();
			result.append(name).append(ENGLISH_COLON).append(request.getHeader(name)).append(LINESEPARATOR);
		}
		return result.toString();
	}
}
