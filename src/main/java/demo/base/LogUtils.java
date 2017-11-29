package demo.base;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogUtils {

	public static void severe(String message) {
		logp(Level.SEVERE, message, null);
	}

	public static void severe(String message, Throwable t) {
		logp(Level.SEVERE, message, t);
	}

	public static void warning(String message) {
		logp(Level.WARNING, message, null);
	}

	public static void warning(String message, Throwable t) {
		logp(Level.WARNING, message, t);
	}

	public static void info(String message) {
		logp(Level.INFO, message, null);
	}

	public static void info(String message, Throwable t) {
		logp(Level.INFO, message, t);
	}

	public static void config(String message) {
		logp(Level.CONFIG, message, null);
	}

	public static void config(String message, Throwable t) {
		logp(Level.CONFIG, message, t);
	}

	public static void fine(String message) {
		logp(Level.FINE, message, null);
	}

	public static void fine(String message, Throwable t) {
		logp(Level.FINE, message, t);
	}

	public static void finer(String message) {
		logp(Level.FINER, message, null);
	}

	public static void finer(String message, Throwable t) {
		logp(Level.FINER, message, t);
	}

	public static void finest(String message) {
		logp(Level.FINEST, message, null);
	}

	public static void finest(String message, Throwable t) {
		logp(Level.FINEST, message, t);
	}

	public static boolean isSevereEnabled() {
		return isLoggable(Level.SEVERE);
	}

	public static boolean isWarningEnabled() {
		return isLoggable(Level.WARNING);
	}

	public static boolean isInfoEnabled() {
		return isLoggable(Level.INFO);
	}

	public static boolean isConfigEnabled() {
		return isLoggable(Level.CONFIG);
	}

	public static boolean isFineEnabled() {
		return isLoggable(Level.FINE);
	}

	public static boolean isFinerEnabled() {
		return isLoggable(Level.FINER);
	}

	public static boolean isFinestEnabled() {
		return isLoggable(Level.FINEST);
	}

	private static void logp(Level level, String message, Throwable t) {
		String clazzName = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		getLogger(clazzName).logp(level, clazzName, methodName, message, t);
	}

	private static boolean isLoggable(Level level) {
		String clazzName = Thread.currentThread().getStackTrace()[3].getClassName();
		return getLogger(clazzName).isLoggable(level);
	}

	private static Logger getLogger(String clazzName) {
		Logger log = LOG_MANAGER.getLogger(clazzName);
		if(log == null) {
			log = Logger.getLogger(clazzName);
			LOG_MANAGER.addLogger(log);
		}
		return log;
	}

	private static final LogManager LOG_MANAGER = LogManager.getLogManager();

}
