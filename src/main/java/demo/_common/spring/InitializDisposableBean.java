package demo._common.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 当bean被初始化和销毁时触发
 * 需要将其子类配置到spring配置文件中
 *
 * Add subclass in applicationContext.xml
 * <pre>
 *     <bean class="subclass reference" />
 * </pre>
 * approach to
 * <pre>
 *     <bean class="reference" init-method="init" destroy-method="destroy" />
 * </pre>
 */
public abstract class InitializDisposableBean implements InitializingBean, DisposableBean {
}
