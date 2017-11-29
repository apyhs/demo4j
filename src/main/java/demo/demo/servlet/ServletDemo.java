package demo.demo.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class ServletDemo {

	public static void summary(HttpServletRequest request) {
		System.out.println("remoteAddr : " + request.getRemoteAddr());
		System.out.println("remotePort : " + request.getRemotePort());
		System.out.println("method : " + request.getMethod());

		// parameters
		System.out.println("parameters : ");
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
		if (entries.size() != 0) {
			for (Map.Entry<String, String[]> entry : entries) {
				String[] values = entry.getValue();
				if (values.length == 1) {
					System.out.print(entry.getKey());
					System.out.print("=");
					System.out.println(values[0]);
				} else {
					System.out.print(entry.getKey());
					System.out.print("[");
					for (String value : values) {
						System.out.print(value);
						System.out.print(",");
					}
					System.out.println("]");
				}
			}
		}

		// cookies
		System.out.println("cookies : ");
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				System.out.print(cookie.getName());
				System.out.print("=");
				System.out.print(cookie.getValue());
			}
		}

		System.out.println("contentType" +  request.getContentType());
		System.out.println("characterEncoding" +  request.getCharacterEncoding());
		System.out.println("requestURL" +  request.getRequestURL());
	}

	public static void headers(HttpServletRequest request) {
		System.out.println("headers : ");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			System.out.print(headerName);
			System.out.print(" : ");
			System.out.println(request.getHeader(headerName));
		}
	}

}
