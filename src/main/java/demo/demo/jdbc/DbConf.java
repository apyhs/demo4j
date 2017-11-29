package demo.demo.jdbc;

import org.springframework.util.StringUtils;
import demo.common.Prop;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

public abstract class DbConf {
	private static volatile Object locker = new Object();
	protected static DataSource dataSource;
	protected String jdbcUrl;
	protected String user;
	protected String password;
	protected String driverClass = "com.mysql.jdbc.Driver";
	protected int maxPoolSize = 100;
	protected int minPoolSize = 10;
	protected int initialPoolSize = 10;
	protected int maxIdleTime = 20;
	protected int acquireIncrement = 2;
	protected boolean isInit = false;
	
	public DbConf setDriverClass(String driverClass) {
		if (StringUtils.hasText(driverClass))
			throw new IllegalArgumentException("driverClass can not be blank.");
		this.driverClass = driverClass;
		return this;
	}
	
	public DbConf setMaxPoolSize(int maxPoolSize) {
		if (maxPoolSize < 1)
			throw new IllegalArgumentException("maxPoolSize must more than 0.");
		this.maxPoolSize = maxPoolSize;
		return this;
	}
	
	public DbConf setMinPoolSize(int minPoolSize) {
		if (minPoolSize < 1)
			throw new IllegalArgumentException("minPoolSize must more than 0.");
		this.minPoolSize = minPoolSize;
		return this;
	}
	
	public DbConf setInitialPoolSize(int initialPoolSize) {
		if (initialPoolSize < 1)
			throw new IllegalArgumentException("initialPoolSize must more than 0.");
		this.initialPoolSize = initialPoolSize;
		return this;
	}
	
	public DbConf setMaxIdleTime(int maxIdleTime) {
		if (maxIdleTime < 1)
			throw new IllegalArgumentException("maxIdleTime must more than 0.");
		this.maxIdleTime = maxIdleTime;
		return this;
	}
	
	public DbConf setAcquireIncrement(int acquireIncrement) {
		if (acquireIncrement < 1)
			throw new IllegalArgumentException("acquireIncrement must more than 0.");
		this.acquireIncrement = acquireIncrement;
		return this;
	}
	
	public DbConf(String jdbcUrl, String user, String password) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
	}
	
	public DbConf(String jdbcUrl, String user, String password, String driverClass) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
	}
	
	public DbConf(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		initConf(jdbcUrl, user, password, driverClass, maxPoolSize, minPoolSize, initialPoolSize, maxIdleTime, acquireIncrement);
	}
	
	public DbConf(File propertyfile) throws Exception {
		Properties ps = Prop.on(propertyfile).getProperties();
		initConf(ps.getProperty("jdbcUrl"), ps.getProperty("user"), ps.getProperty("password"), ps.getProperty("driverClass"),
				toInt(ps.getProperty("maxPoolSize")), toInt(ps.getProperty("minPoolSize")), toInt(ps.getProperty("initialPoolSize")),
				toInt(ps.getProperty("maxIdleTime")),toInt(ps.getProperty("acquireIncrement")));
	}
	
	public DbConf(Properties properties) {
		Properties ps = properties;
		initConf(ps.getProperty("jdbcUrl"), ps.getProperty("user"), ps.getProperty("password"), ps.getProperty("driverClass"),
				toInt(ps.getProperty("maxPoolSize")), toInt(ps.getProperty("minPoolSize")), toInt(ps.getProperty("initialPoolSize")),
				toInt(ps.getProperty("maxIdleTime")),toInt(ps.getProperty("acquireIncrement")));
	}
	
	private Integer toInt(String str) {
		return Integer.parseInt(str);
	}
	
	private void initConf(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		this.jdbcUrl = jdbcUrl;
		this.user = user;
		this.password = password;
		this.driverClass = driverClass != null ? driverClass : this.driverClass;
		this.maxPoolSize = maxPoolSize != null ? maxPoolSize : this.maxPoolSize;
		this.minPoolSize = minPoolSize != null ? minPoolSize : this.minPoolSize;
		this.initialPoolSize = initialPoolSize != null ? initialPoolSize : this.initialPoolSize;
		this.maxIdleTime = maxIdleTime != null ? maxIdleTime : this.maxIdleTime;
		this.acquireIncrement = acquireIncrement != null ? acquireIncrement : this.acquireIncrement;
	}
	
	public DataSource getDataSource() {
		if(isInit == false) {
			synchronized (locker) {
				if(isInit == false) init();
			}
		} return dataSource;
	}
	
	public abstract boolean init();
	
	public abstract boolean destroy();

}
