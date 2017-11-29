package demo.jfinal;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class LogsInterceptor implements Interceptor  {

	@Override
	public void intercept(Invocation inv) {
		HttpServletRequest request = inv.getController().getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String str = (String) headerNames.nextElement();
			System.out.println(str + " : " + request.getHeader(str));
		}
		System.out.println("-----------------------------------------");
		System.out.print("paraMap : ");
		Map<String, String[]> paraMap = request.getParameterMap();
		for (String key : paraMap.keySet()) { 
			System.out.print(key + " : " + Arrays.toString(paraMap.get(key)));
		}
		System.out.println();
		Cookie[] cookies = request.getCookies();
		System.out.println("cookies : "+ Arrays.toString(cookies));
		System.out.println("requestURL : "+request.getRequestURL().toString());
		System.out.println("method : " + request.getMethod());
		System.out.println("Server Addr Name Port : " + request.getLocalAddr()+" | "+request.getLocalName()+" | "+request.getLocalPort());
		System.out.println("ContentType : " + request.getContentType());
		System.out.println("CharacterEncoding : " + request.getCharacterEncoding());
		System.out.println("RemoteAddr RemoteHost RemotePort : " + request.getRemoteAddr()+" | "+request.getRemoteHost()+" | "+request.getRemotePort());
		System.out.println();
		System.out.println();
		inv.invoke();
	}

}
