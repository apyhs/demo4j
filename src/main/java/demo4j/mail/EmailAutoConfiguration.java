package demo4j.mail;

import artoria.util.ClassLoaderUtils;
import artoria.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Email auto configuration.
 * @author Kahle
 */
//@Configuration
public class EmailAutoConfiguration implements InitializingBean, DisposableBean {
    private static final String MAIL_CLASS = "javax.mail.Message";
    private static Logger log = LoggerFactory.getLogger(artoria._mail.EmailAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void destroy() throws Exception {
    }

    @Bean
    @ConditionalOnMissingBean
    public EmailClient emailClient() {
        ClassLoader loader = ClassLoaderUtils.getDefaultClassLoader();
        if (!ClassUtils.isPresent(MAIL_CLASS, loader)) {
            log.error("Can not find jar \"javax.mail\". ");
            return null;
        }
        return new EmailClient();
    }

}
