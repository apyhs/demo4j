package fstart.spring.ssh.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 前置处理，在Controller处理之前进行调用
        System.out.println(">>>>>>>>preHandle");
        return true; // 如果 false，则结束请求了
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // 在Controller调用之后，DispatcherServlet进行视图渲染之前执行
        System.out.println(">>>>>>>>postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // 在整个请求完成之后，即DispatcherServlet渲染视图之后执行，一般用来释放资源
        System.out.println(">>>>>>>>afterCompletion");
    }

}
