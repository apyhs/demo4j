package demo;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogKit {
	private static final LogManager logManager = LogManager.getLogManager();

	private static String currentClazzName() {
		return Thread.currentThread().getStackTrace()[3].getClassName();
	}

	private static String currentMethodName() {
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}

	private static Logger log(String clazzName) {
		Logger log = logManager.getLogger(clazzName);
		if(log == null) {
			log = Logger.getLogger(clazzName);
			logManager.addLogger(log);
		} return log;
	}

	public static void debug(String message) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.FINE, clazzName, currentMethodName(), message);
	}

	public static void debug(String message, Throwable t) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.FINE, clazzName, currentMethodName(), message, t);
	}

	public static void info(String message) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.INFO, clazzName, currentMethodName(), message);
	}

	public static void info(String message, Throwable t) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.INFO, clazzName, currentMethodName(), message, t);
	}

	public static void warn(String message) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.WARNING, clazzName, currentMethodName(), message);
	}

	public static void warn(String message, Throwable t) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.WARNING, clazzName, currentMethodName(), message, t);
	}

	public static void error(String message) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.SEVERE, clazzName, currentMethodName(), message);
	}

	public static void error(String message, Throwable t) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.SEVERE, clazzName, currentMethodName(), message, t);
	}

	public static void fatal(String message) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.SEVERE, clazzName, currentMethodName(), message);
	}

	public static void fatal(String message, Throwable t) {
		String clazzName = currentClazzName();
		log(clazzName).logp(Level.SEVERE, clazzName, currentMethodName(), message, t);
	}

	public static boolean isDebugEnabled() {
		return log(currentClazzName()).isLoggable(Level.FINE);
	}

	public static boolean isInfoEnabled() {
		return log(currentClazzName()).isLoggable(Level.INFO);
	}

	public static boolean isWarnEnabled() {
		return log(currentClazzName()).isLoggable(Level.WARNING);
	}

	public static boolean isErrorEnabled() {
		return log(currentClazzName()).isLoggable(Level.SEVERE);
	}

	public static boolean isFatalEnabled() {
		return log(currentClazzName()).isLoggable(Level.SEVERE);
	}

}
