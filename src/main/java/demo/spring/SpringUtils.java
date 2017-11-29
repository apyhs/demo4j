package demo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Add in applicationContext.xml
 * <pre>
 *     <bean class="saber.spring.SpringUtils" lazy-init="false" />
 * </pre>
 */
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static Object getBean(String name, Object... args) {
        return applicationContext.getBean(name, args);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return applicationContext.getBean(requiredType, args);
    }

}
