package demo.jdk;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogKit {

	public static void debug(String message) {
		logging(Level.FINE, message);
	}

	public static void debug(String message, Throwable t) {
		logging(Level.FINE, message, t);
	}

	public static void info(String message) {
		logging(Level.INFO, message);
	}

	public static void info(String message, Throwable t) {
		logging(Level.INFO, message, t);
	}

	public static void warn(String message) {
		logging(Level.WARNING, message);
	}

	public static void warn(String message, Throwable t) {
		logging(Level.WARNING, message, t);
	}

	public static void error(String message) {
		logging(Level.SEVERE, message);
	}

	public static void error(String message, Throwable t) {
		logging(Level.SEVERE, message, t);
	}

	public static void fatal(String message) {
		logging(Level.SEVERE, message);
	}

	public static void fatal(String message, Throwable t) {
		logging(Level.SEVERE, message, t);
	}

	public static boolean isDebugEnabled() {
		return isLoggable(Level.FINE);
	}

	public static boolean isInfoEnabled() {
		return isLoggable(Level.INFO);
	}

	public static boolean isWarnEnabled() {
		return isLoggable(Level.WARNING);
	}

	public static boolean isErrorEnabled() {
		return isLoggable(Level.SEVERE);
	}

	public static boolean isFatalEnabled() {
		return isLoggable(Level.SEVERE);
	}

	private static boolean isLoggable(Level level) {
		String clazzName = Thread.currentThread().getStackTrace()[3].getClassName();
		return getLogger(clazzName).isLoggable(level);
	}

	private static void logging(Level level, String message) {
		String clazzName = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		getLogger(clazzName).logp(level, clazzName, methodName, message);
	}

	private static void logging(Level level, String message, Throwable t) {
		String clazzName = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		getLogger(clazzName).logp(level, clazzName, methodName, message, t);
	}

	private static Logger getLogger(String clazzName) {
		Logger log = logManager.getLogger(clazzName);
		if(log == null) {
			log = Logger.getLogger(clazzName);
			logManager.addLogger(log);
		}
		return log;
	}

	private static final LogManager logManager = LogManager.getLogManager();

}
