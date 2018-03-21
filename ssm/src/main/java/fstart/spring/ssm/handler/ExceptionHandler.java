package fstart.spring.ssm.handler;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        e.printStackTrace();

        if (e instanceof RuntimeException) {
            return new ModelAndView("error", "msg", "RuntimeException");
        } else {
            return new ModelAndView("error", "msg", e.getMessage());
        }
    }

}
