package demo.log;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;

/**
 * 日志处理的工具类，主要是方便了不用if，并且方便了异常打印
 * @author Anmt
 * @blog http://anmt.me
 * @email anmtme@gmail.com
 */
public class LoggerUtils {

	public static String LINESEPARATOR = System.getProperty("line.separator");

	public static void debugMsg(Logger logger, Exception e) {
		if (logger.isDebugEnabled()) {
			StringBuffer strb = new StringBuffer(LINESEPARATOR);
			// 将异常信息写入字符流，将字符流append到StringBuffer上
			StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        e.printStackTrace(writer);
	        strb.append(stringWriter.getBuffer());
	        // 写入日志
	        logger.debug(strb.toString());
		}
	}

	public static void debugMsg(Logger logger, Exception e, String customMsg) {
		if (logger.isDebugEnabled()) {
			StringBuffer strb = new StringBuffer(customMsg).append(LINESEPARATOR);
			// 将异常信息写入字符流，将字符流append到StringBuffer上
			StringWriter stringWriter= new StringWriter();
	        PrintWriter writer= new PrintWriter(stringWriter);
	        e.printStackTrace(writer);
	        strb.append(stringWriter.getBuffer());
	        // 写入日志
			logger.debug(strb.toString());
		}
	}
	
	public static void debugMsg(Logger logger, String customMsg) {
		if (logger.isDebugEnabled())
			logger.debug(customMsg);
	}

	public static void infoMsg(Logger logger, String customMsg) {
		if (logger.isInfoEnabled())
			logger.info(customMsg);
	}

	public static void warnMsg(Logger logger, String customMsg) {
		if (logger.isWarnEnabled())
			logger.warn(customMsg);
	}

	public static void errorMsg(Logger logger, String customMsg) {
		if (logger.isErrorEnabled())
			logger.error(customMsg);
	}

	public static void traceMsg(Logger logger, String customMsg) {
		if (logger.isTraceEnabled())
			logger.trace(customMsg);
	}
}
