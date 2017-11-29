package demo.jfinal.configuration;

import demo.jfinal.interceptor.ExceptionInterceptor;
import demo.jfinal.interceptor.LogsInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

import com.jfinal.template.Engine;

/**
 * API引导式配置
 */
public class GlobalConf extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}
	
	/**
	 * 配置路由
	 */
	@Override
	public void configRoute(Routes me) {
//		me.add("/test", TestController.class, "/test");
	}

	@Override
	public void configEngine(Engine engine) {

	}

	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
//		C3p0Plugin C3p0Plugin = createC3p0Plugin();
//		me.add(C3p0Plugin);
		
		// 配置ActiveRecord插件
//		ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
//		me.add(arp);
		
		// 所有配置在 MappingKit 中搞定
//		_MappingKit.mapping(arp);
	}
	
	/**
	 * 配置全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// 全局Action的异常处理拦截器
		me.addGlobalActionInterceptor(new ExceptionInterceptor());
		me.addGlobalActionInterceptor(new LogsInterceptor());
	}
	
	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		
	}
	
	/**
	 * 创建C3p0Plugin对象
	 * @return C3p0Plugin对象
	 */
//	public static C3p0Plugin createC3p0Plugin() {
//		return new C3p0Plugin();
//	}
	
	/**
	 * 建议使用 JFinal 官方手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}
	
}
