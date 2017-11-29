package demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogKit {

	private static class Holder {
		private static Logger log = LoggerFactory.getLogger(LogKit.class);
	}

	public static void trace(String message) {
		Holder.log.trace(message);
	}

	public static void trace(String message, Throwable t) {
		Holder.log.trace(message, t);
	}

	public static void debug(String message) {
		Holder.log.debug(message);
	}

	public static void debug(String message, Throwable t) {
		Holder.log.debug(message, t);
	}

	public static void info(String message) {
		Holder.log.info(message);
	}

	public static void info(String message, Throwable t) {
		Holder.log.info(message, t);
	}

	public static void warn(String message) {
		Holder.log.warn(message);
	}

	public static void warn(String message, Throwable t) {
		Holder.log.warn(message, t);
	}

	public static void error(String message) {
		Holder.log.error(message);
	}

	public static void error(String message, Throwable t) {
		Holder.log.error(message, t);
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
