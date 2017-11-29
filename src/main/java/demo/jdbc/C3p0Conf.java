package demo.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.File;
import java.util.Properties;

public class C3p0Conf extends DbConf {
	
	public ComboPooledDataSource getComboPooledDataSource() {
		return (ComboPooledDataSource)getDataSource();
	}
	
	public C3p0Conf(String jdbcUrl, String user, String password) {
		super(jdbcUrl, user, password);
	}
	
	public C3p0Conf(String jdbcUrl, String user, String password, String driverClass) {
		super(jdbcUrl, user, password, driverClass);
	}
	
	public C3p0Conf(String jdbcUrl, String user, String password, String driverClass, Integer maxPoolSize, Integer minPoolSize, Integer initialPoolSize, Integer maxIdleTime, Integer acquireIncrement) {
		super(jdbcUrl, user, password, driverClass, maxPoolSize, minPoolSize, initialPoolSize, maxIdleTime, acquireIncrement);
	}
	
	public C3p0Conf(File propertyfile)  throws Exception  {
		super(propertyfile);
	}
	
	public C3p0Conf(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean init() {
		if (isInit) return true;
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl(jdbcUrl);
		ds.setUser(user);
		ds.setPassword(password);
		try { ds.setDriverClass(driverClass); }
		catch (Exception e) { throw new RuntimeException(e); } 
		ds.setMaxPoolSize(maxPoolSize);
		ds.setMinPoolSize(minPoolSize);
		ds.setInitialPoolSize(initialPoolSize);
		ds.setMaxIdleTime(maxIdleTime);
		ds.setAcquireIncrement(acquireIncrement);
		ds.setTestConnectionOnCheckin(true);
		ds.setTestConnectionOnCheckout(true);
		dataSource = ds; isInit = true;
		return true;
	}
	
	@Override
	public boolean destroy() {
		if (dataSource != null)
			((ComboPooledDataSource)dataSource).close();
		dataSource = null;
		isInit = false;
		return true;
	}

}
