package demo.function.logging.support;

import artoria.io.IOUtils;
import artoria.util.ArrayUtils;
import artoria.util.ClassUtils;
import artoria.util.Const;
import demo.function.logging.Level;
import demo.function.logging.Logger;
import demo.function.logging.LoggerAdapter;

import java.io.InputStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogManager;

/**
 * Jdk logger adapter.
 * @author Kahle
 */
public class JdkLoggerAdapter implements LoggerAdapter {

	/**
	 * Default logger config filename.
	 */
	private static final String LOGGER_CONFIG_FILENAME = "logging.properties";

	/**
	 * This is JDK root logger name.
	 */
	private static final String ROOT_LOGGER_NAME = "";

	private java.util.logging.Logger log;

	public JdkLoggerAdapter() {
		log = java.util.logging.Logger.getLogger(ROOT_LOGGER_NAME);
		ClassLoader loader = ClassUtils.getDefaultClassLoader();
		InputStream in = loader != null
				? loader.getResourceAsStream(LOGGER_CONFIG_FILENAME)
				: ClassLoader.getSystemResourceAsStream(LOGGER_CONFIG_FILENAME);
		if (in == null) {
			in = IOUtils.findClasspath(LOGGER_CONFIG_FILENAME);
		}
		if (in != null) {
			try {
				LogManager.getLogManager().readConfiguration(in);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				IOUtils.closeQuietly(in);
			}
		}
		// Because LogManager's class will other.
		// So maybe readConfiguration is not useful.
		Handler[] handlers = log.getHandlers();
		if (ArrayUtils.isEmpty(handlers)) { return; }
		SimpleFormatter formatter = new SimpleFormatter();
		for (Handler handler : handlers) {
			if (handler == null) { continue; }
			Formatter fm = handler.getFormatter();
			if (fm == null) { continue; }
			Class<? extends Formatter> cls = fm.getClass();
			if (cls.equals(java.util.logging.SimpleFormatter.class)) {
				handler.setFormatter(formatter);
			}
		}
	}

	@Override
	public Logger getLogger(Class<?> clazz) {
		String name = clazz == null ? Const.EMPTY_STRING : clazz.getName();
		return new JdkLogger(java.util.logging.Logger.getLogger(name));
	}

	@Override
	public Logger getLogger(String clazz) {
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(clazz);
		return new JdkLogger(logger);
	}

	@Override
	public Level getLevel() {
		java.util.logging.Level level = log.getLevel();
		if (level == java.util.logging.Level.FINER) {
			return Level.TRACE;
		}
		if (level == java.util.logging.Level.FINE) {
			return Level.DEBUG;
		}
		if (level == java.util.logging.Level.INFO) {
			return Level.INFO;
		}
		if (level == java.util.logging.Level.WARNING) {
			return Level.WARN;
		}
		if (level == java.util.logging.Level.SEVERE) {
			return Level.ERROR;
		}
		return null;
	}

	@Override
	public void setLevel(Level level) {
		if (level == Level.TRACE) {
			log.setLevel(java.util.logging.Level.FINER);
		}
		if (level == Level.DEBUG) {
			log.setLevel(java.util.logging.Level.FINE);
		}
		if (level == Level.INFO) {
			log.setLevel(java.util.logging.Level.INFO);
		}
		if (level == Level.WARN) {
			log.setLevel(java.util.logging.Level.WARNING);
		}
		if (level == Level.ERROR) {
			log.setLevel(java.util.logging.Level.SEVERE);
		}
	}

}