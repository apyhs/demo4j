package demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogKit {
	
	private static class Holder {
		private static Logger log = LoggerFactory.getLogger(LogKit.class);
	}

	public static void trace(String message, Object... args) {
		Holder.log.trace(String.format(message, args));
	}
	
	public static void trace(String message, Throwable t, Object... args) {
		Holder.log.trace(String.format(message, args), t);
	}
	
	public static void debug(String message, Object... args) {
		Holder.log.debug(String.format(message, args));
	}
	
	public static void debug(String message, Throwable t, Object... args) {
		Holder.log.debug(String.format(message, args), t);
	}
	
	public static void info(String message, Object... args) {
		Holder.log.info(String.format(message, args));
	}
	
	public static void info(String message, Throwable t, Object... args) {
		Holder.log.info(String.format(message, args), t);
	}
	
	public static void warn(String message, Object... args) {
		Holder.log.warn(String.format(message, args));
	}
	
	public static void warn(String message, Throwable t, Object... args) {
		Holder.log.warn(String.format(message, args), t);
	}
	
	public static void error(String message, Object... args) {
		Holder.log.error(String.format(message, args));
	}
	
	public static void error(String message, Throwable t, Object... args) {
		Holder.log.error(String.format(message, args), t);
	}

	public static boolean isTraceEnabled() {
		return Holder.log.isTraceEnabled();
	}
	
	public static boolean isDebugEnabled() {
		return Holder.log.isDebugEnabled();
	}
	
	public static boolean isInfoEnabled() {
		return Holder.log.isInfoEnabled();
	}
	
	public static boolean isWarnEnabled() {
		return Holder.log.isWarnEnabled();
	}
	
	public static boolean isErrorEnabled() {
		return Holder.log.isErrorEnabled();
	}
}
