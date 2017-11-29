package demo.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * slf4j日志的一个包装类，将系统运行分为三种状态，起始、运行、结束
 * @author Anmt
 * @blog http://anmt.me
 * @email anmtme@gmail.com
 */
public class LogOuter {
	
	/**
	 * 跨平台换行符  >> line.separator
	 */
	private static String LINESEPARATOR = System.getProperty("line.separator");
	
	/**
	 * slf4j的日志操作对象
	 */
	private Logger logger = null;
	
	/**
	 * 该包装类的唯一性ID，用于多线程环境下区分不同请求的日志
	 */
	private String outputerId = null;
	
	/**
	 * 开始处理本次请求时的时间戳
	 */
	private Long startTime = 0L;
	
	/**
	 * 本次请求处理完成的时间戳
	 */
	private Long endTime = 0L;

	/**
	 * 构造方法，传入slf4j的操作对象，并且书写起始态的日志
	 * @param logger slf4j的操作对象
	 * @param format 起始态的日志的格式化字符串
	 * @param args 格式化字符串中对应的对象
	 */
	public LogOuter(Logger logger, String format, Object... args) {
		this.logger = logger;
		outputerId = this.createOutputerId();
		this.start(format, args);
	}

	/**
	 * 构造方法，传入调用本包装类的那个对象（一般可以this）,并书写起始态的日志
	 * @param clazz 调用本包装类的那个对象
	 * @param format 格式化的起始态日志的字符串
	 * @param args 格式化字符串对应的对象
	 */
	public <T> LogOuter(T clazz, String format, Object... args) {
		this.logger = LoggerFactory.getLogger(clazz.getClass());
		outputerId = this.createOutputerId();
		this.start(format, args);
	}

	/**
	 * 起始态日志信息，在构造方法时被调用
	 * @param format 格式化的信息字符串
	 * @param args 格式化字符串对应的对象
	 */
	private void start(String format, Object... args) {
		if(this.logger.isInfoEnabled()){
			this.logger.info(String.format("%s >> start >> ", this.outputerId) + String.format(format, args));
		}
		this.startTime = Long.valueOf(System.currentTimeMillis());
	}

	/**
	 * 运行态的日志信息，用于描述系统的运行状态
	 * @param format 格式化的信息字符串
	 * @param args 格式化字符串对应的对象
	 */
	public void run4Info(String format, Object... args) {
		if(this.logger.isInfoEnabled()){
			this.logger.info(String.format("%s >> running >> ", this.outputerId) + String.format(format, args));
		}
	}

	/**
	 * 运行态的日志警告，用于描述系统的运行状态
	 * @param format
	 * @param args
	 */
	public void run4Warn(String format, Object... args) {
		if(this.logger.isWarnEnabled()){
			this.logger.warn(String.format("%s >> running >> ", this.outputerId) + String.format(format, args));
		}
	}

	/**
	 * 运行态的日志错误，用于描述系统的运行状态
	 * @param format
	 * @param args
	 */
	public void run4Error(String format, Object... args) {
		if(this.logger.isErrorEnabled()){
			this.logger.error(String.format("%s >> running >> ", this.outputerId) + String.format(format, args));
		}
	}

	/**
	 * 成功结束的日志
	 * @param format
	 * @param args
	 */
	public void end4Success(String format, Object... args) {
		this.end4TimeCosts();
		if(this.logger.isInfoEnabled()){
			this.logger.info(String.format("%s << end << success << ", this.outputerId) + String.format(format, args));
		}
		// end只能调用一次
		this.logger = null;
	}

	/**
	 * 失败结束的日志
	 * @param format
	 * @param args
	 */
	public void end4Failure(String format, Object... args) {
		this.end4TimeCosts();
		if(this.logger.isWarnEnabled()){
			this.logger.warn(String.format("%s << end << failure << ", this.outputerId) + String.format(format, args));
		}
		// end只能调用一次
		this.logger = null;
	}

	/**
	 * 异常结束的日志
	 * @param e
	 * @param format
	 * @param args
	 */
	public void end4Exception(Throwable e, String format, Object... args) {
		this.end4TimeCosts();
		if(format == null) format = new String("<<====");
		if(this.logger.isErrorEnabled()){
			this.logger.error(String.format("%s << end << exception << ", this.outputerId) + String.format(format, args));
		}
		if(this.logger.isDebugEnabled()){
			this.logger.debug(String.format("%s << end << exception << ", this.outputerId) + String.format(format, args) + LINESEPARATOR + exception2String(e));
		}
		// end只能调用一次
		this.logger = null;
	}

	/**
	 * 结束时运行时间花费计算，被包含在结束态中
	 */
	private void end4TimeCosts() {
		this.endTime = Long.valueOf(System.currentTimeMillis());
		if(this.logger.isInfoEnabled()){
			this.logger.info(String.format("%s << end << time << ", this.outputerId) + (this.endTime - this.startTime) + "ms");
		}
	}
	
	/**
	 * 创建本包装类的唯一性ID >> 即UUID
	 * @return 唯一性ID的字符串
	 */
	private String createOutputerId() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	/**
	 * 将异常对象转换成异常信息字符串  >> 即printStackTrace打印到控制台的内容
	 * @param e 异常对象
	 * @return 异常信息
	 */
	private static String exception2String(Throwable e) {
		// 将异常信息写入字符流，再从字符流中获取到StringBuffer
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		e.printStackTrace(writer);
		return stringWriter.getBuffer().toString();
	}
}
