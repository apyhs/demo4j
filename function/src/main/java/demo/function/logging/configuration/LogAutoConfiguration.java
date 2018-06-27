package demo.function.logging.configuration;

import artoria.util.ClassUtils;
import demo.function.logging.Logger;
import demo.function.logging.LoggerFactory;
import demo.function.logging.support.Log4jLoggerAdapter;
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
