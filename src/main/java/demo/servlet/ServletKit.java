package demo.servlet;

import demo.StrKit;
import demo.StreamKit;
import demo.TimeKit;
import com.oreilly.servlet.MultipartRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class ServletKit {
	private static final String BRACKETS_LEFT = "[";
	private static final String BRACKETS_RIGHT = "]";
	private static final String BLANK_SPACE = " ";
	private static final String ENGLISH_COLON = ":";
	private static final String ENGLISH_COMMA = ",";
	private static final String CHARACTER_EQUAL = "=";
	private static final String BRACES_LEFT = "{";
	private static final String BRACES_RIGHT = "}";
	private static final String CHARACTER_TO = "To";
	private static final String CHARACTER_AT = "At";
	private static final String CHARACTER_CONTENT_TYPE = "ContentType";
	private static final String CHARACTER_ENCODE = "Encode";
	private static final String CHARACTER_PARAMETER = "Parameter";
	private static final String CHARACTER_COOKIE = "Cookie";
	
	/**
	 * get request information
	 */
	public static String info(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(CHARACTER_AT).append(BRACKETS_LEFT)
		.append(TimeKit.format()).append(BRACKETS_RIGHT).append(BLANK_SPACE);
		
		buffer.append(BRACKETS_LEFT).append(request.getRemoteAddr())
		.append(ENGLISH_COLON).append(request.getRemotePort())
		.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		
		buffer.append(request.getMethod()).append(BLANK_SPACE);
		
		String parameter = parameterInfo(request);
		buffer.append(parameter);
		if(parameter.trim().length() != 0) buffer.append(BLANK_SPACE);
		
		String cookie = cookieInfo(request);
		buffer.append(cookie);
		if(cookie.trim().length() != 0) buffer.append(BLANK_SPACE);
		
		String contentType = contentType(request);
		buffer.append(contentType);
		if(contentType.trim().length() != 0) buffer.append(BLANK_SPACE);
		
		String encode = encodeInfo(request);
		buffer.append(encode);
		if(encode.trim().length() != 0) buffer.append(BLANK_SPACE);
		
		buffer.append(CHARACTER_TO).append(BLANK_SPACE).append(BRACKETS_LEFT)
		.append(request.getRequestURL().toString()).append(BRACKETS_RIGHT);
		
		return buffer.toString();
	}
	
	/**
	 * get request content type information
	 */
	public static String contentType(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		String contentType = request.getContentType();
		if(contentType != null && contentType.trim().length() > 0){
			buffer.append(CHARACTER_CONTENT_TYPE).append(BRACKETS_LEFT)
			.append(contentType).append(BRACKETS_RIGHT);
		}
		return buffer.toString();
	}
	
	/**
	 * get request encode information
	 */
	public static String encodeInfo(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		String characterEncoding = request.getCharacterEncoding();
		if(characterEncoding != null && characterEncoding.trim().length() > 0){
			buffer.append(CHARACTER_ENCODE).append(BRACKETS_LEFT)
			.append(characterEncoding).append(BRACKETS_RIGHT);
		}
		return buffer.toString();
	}
	
	/**
	 * get request cookie information
	 */
	public static String cookieInfo(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			buffer.append(CHARACTER_COOKIE).append(BRACKETS_LEFT);
			for (Cookie cookie : cookies) {
				buffer.append(cookie.getName()).append(CHARACTER_EQUAL)
				.append(cookie.getValue()).append(ENGLISH_COMMA);
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append(BRACKETS_RIGHT);
		}
		return buffer.toString();
	}
	
	/**
	 * get MultipartRequest parameter information
	 */
	public static String parameterInfo(MultipartRequest request){
		StringBuffer buffer = new StringBuffer();
		buffer.append(CHARACTER_PARAMETER).append(BRACKETS_LEFT);
		Enumeration<?> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String[] values = request.getParameterValues(name);
			if(values.length == 1){
				buffer.append(name).append(CHARACTER_EQUAL).append(values[0])
				.append(ENGLISH_COMMA);
			}else{
				buffer.append(name).append(BRACKETS_LEFT).append(BRACKETS_RIGHT)
				.append(CHARACTER_EQUAL).append(BRACES_LEFT);
				for (String value : values) {
					buffer.append(value).append(ENGLISH_COMMA);
				}
				buffer.deleteCharAt(buffer.length() - 1).append(BRACES_RIGHT)
				.append(ENGLISH_COMMA);
			}
		}
		
		Enumeration<?> fileNames = request.getFileNames();
		while (fileNames.hasMoreElements()) {
			String fileName = (String) fileNames.nextElement();
			File file = request.getFile(fileName);
			buffer.append(fileName).append(CHARACTER_EQUAL).append(file.toString())
			.append(ENGLISH_COMMA);
		}
		
		buffer.deleteCharAt(buffer.length() - 1).append(BRACKETS_RIGHT);
		return buffer.toString();
	}
	
	/**
	 * get request parameter information
	 */
	public static String parameterInfo(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<String> keys = parameterMap.keySet();
		if(keys.size() != 0){
			buffer.append(CHARACTER_PARAMETER).append(BRACKETS_LEFT);
			for (String key : keys) {
				String[] values = parameterMap.get(key);
				if(values.length == 1){
					buffer.append(key).append(CHARACTER_EQUAL).append(values[0])
					.append(ENGLISH_COMMA);
				}else{
					buffer.append(key).append(BRACKETS_LEFT).append(BRACKETS_RIGHT)
					.append(CHARACTER_EQUAL).append(BRACES_LEFT);
					for (String value : values) {
						buffer.append(value).append(ENGLISH_COMMA);
					}
					buffer.deleteCharAt(buffer.length() - 1).append(BRACES_RIGHT)
					.append(ENGLISH_COMMA);
				}
			}
			buffer.deleteCharAt(buffer.length() - 1).append(BRACKETS_RIGHT);
		}
		return buffer.toString();
	}
	
	/**
	 * get request header information
	 */
	public static String headerInfo(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = (String) headerNames.nextElement();
			buffer.append(name).append(ENGLISH_COLON).append(request.getHeader(name))
			.append(StrKit.LSEP);
		}
		return buffer.toString();
	}
	
	/**
	 * if hava request body, get it. else return null string
	 */
	public static String bodyInfo(HttpServletRequest request){
		BufferedReader reader = null;
		try {
			StringBuffer buffer = new StringBuffer();
			if(request.getContentLength() != -1){
				reader = request.getReader();
				for(String line = null; (line = reader.readLine()) != null;){
					buffer.append(line).append(StrKit.LSEP);
				}
			}
			return buffer.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			StreamKit.close(reader);
		}
	}
}
