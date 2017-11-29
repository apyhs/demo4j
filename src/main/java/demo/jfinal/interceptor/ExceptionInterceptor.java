package demo.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class ExceptionInterceptor implements Interceptor {
	
	/**
	 * 全局Action的异常处理拦截器
	 */
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			inv.getController().renderJson(500);
		}
	}
}
