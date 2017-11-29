package demo._common.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 当 ApplicationContext 被初始化或者刷新的时候触发
 * 需要将其子类配置到spring配置文件中
 *
 * Add subclass in applicationContext.xml
 * <pre>
 *     <bean class="subclass reference" />
 * </pre>
 */
public abstract class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
}
