package demo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class ServletKit {

	public static boolean isMultipartRequest(HttpServletRequest request) {
		String contentType = request.getContentType();
		return contentType != null && contentType.toLowerCase().contains("multipart");
	}

	//---- request information
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

	public static String summary(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();

		// time
		builder.append(CHARACTER_AT).append(BRACKETS_LEFT)
		.append(TimeKit.format()).append(BRACKETS_RIGHT).append(BLANK_SPACE);

		// remote address and port
		builder.append(BRACKETS_LEFT).append(request.getRemoteAddr())
		.append(ENGLISH_COLON).append(request.getRemotePort())
		.append(BRACKETS_RIGHT).append(BLANK_SPACE);

		// http method
		builder.append(request.getMethod()).append(BLANK_SPACE);

		// parameters
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
		if (entries.size() != 0) {
			builder.append(CHARACTER_PARAMETER).append(BRACKETS_LEFT);
			for (Map.Entry<String, String[]> entry : entries) {
				String[] values = entry.getValue();
				if (values.length == 1) {
					builder.append(entry.getKey()).append(CHARACTER_EQUAL)
					.append(values[0]).append(ENGLISH_COMMA);
				} else {
					builder.append(entry.getKey()).append(BRACKETS_LEFT)
					.append(BRACKETS_RIGHT).append(CHARACTER_EQUAL).append(BRACES_LEFT);
					for (String value : values) {
						builder.append(value).append(ENGLISH_COMMA);
					}
					builder.deleteCharAt(builder.length() - 1)
					.append(BRACES_RIGHT).append(ENGLISH_COMMA);
				}
			}
			builder.deleteCharAt(builder.length() - 1)
			.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}

		// cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			builder.append(CHARACTER_COOKIE).append(BRACKETS_LEFT);
			for (Cookie cookie : cookies) {
				builder.append(cookie.getName()).append(CHARACTER_EQUAL)
						.append(cookie.getValue()).append(ENGLISH_COMMA);
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}

		// content type
		String contentType = request.getContentType();
		if (StrKit.notBlank(contentType)) {
			builder.append(CHARACTER_CONTENT_TYPE).append(BRACKETS_LEFT)
			.append(contentType).append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}

		// character encoding
		String characterEncoding = request.getCharacterEncoding();
		if (StrKit.notBlank(characterEncoding)) {
			builder.append(CHARACTER_ENCODE).append(BRACKETS_LEFT)
			.append(characterEncoding).append(BRACKETS_RIGHT).append(BLANK_SPACE);
		}

		// request url
		builder.append(CHARACTER_TO).append(BLANK_SPACE).append(BRACKETS_LEFT)
		.append(request.getRequestURL().toString()).append(BRACKETS_RIGHT);

		return builder.toString();
	}

	public static String headers(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			builder.append(headerName).append(ENGLISH_COLON).append(BLANK_SPACE)
			.append(request.getHeader(headerName)).append(StrKit.LSEP);
		}
		return builder.toString();
	}

	//----
}
