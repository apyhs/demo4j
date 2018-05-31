package com.github.kahlkn.demo._function.logging.configuration;

import com.github.kahlkn.artoria.util.ClassUtils;
import com.github.kahlkn.demo._function.logging.Logger;
import com.github.kahlkn.demo._function.logging.LoggerFactory;
import com.github.kahlkn.demo._function.logging.support.Log4jLoggerAdapter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Logger auto configuration.
 * @author Kahle
 */
@Configuration
public class LogAutoConfiguration implements InitializingBean, DisposableBean {
    private static Logger log = LoggerFactory.getLogger(LogAutoConfiguration.class);
    private static final String LOG4J_CLASS = "org.apache.log4j.Logger";

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Try find other logger implement...");
        ClassLoader loader = ClassUtils.getDefaultClassLoader();
        if (ClassUtils.isPresent(LOG4J_CLASS, loader)) {
            LoggerFactory.setLoggerAdapter(new Log4jLoggerAdapter());
        }
    }

    @Override
    public void destroy() throws Exception {
    }

}
