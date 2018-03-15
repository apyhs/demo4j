package com.github.kahlkn.demo.jdbc;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DBUtil1 {
	private static QueryRunner runner = new QueryRunner();

	public static void init(DataSource dataSource) {
		runner = new QueryRunner(dataSource);
	}

	public static Object[] queryArray(String sql, Object... params) {
		try {
			return runner.query(sql, new ArrayHandler(), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Object[]> queryListArray(String sql, Object... params) {
		try {
			return runner.query(sql, new ArrayListHandler(), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> queryMap(String sql, Object... params) {
		try {
			return runner.query(sql, new MapHandler(), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Map<String, Object>> queryListMap(String sql, Object... params) {
		try {
			return runner.query(sql, new MapListHandler(), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T queryBean(Class<T> cls, String sql, Object... params) {
		try {
			return runner.query(sql, new BeanHandler<T>(cls), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T queryBean(Class<T> cls, Map<String, String> map, String sql, Object... params) {
		try {
			return runner.query(sql, new BeanHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> queryListBean(Class<T> cls, String sql, Object... params) {
		try {
			return runner.query(sql, new BeanListHandler<T>(cls), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> List<T> queryListBean(Class<T> cls, Map<String, String> map, String sql, Object... params) {
		try {
			return runner.query(sql, new BeanListHandler<T>(cls, new BasicRowProcessor(new BeanProcessor(map))), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T queryColumn(String column, String sql, Object... params) {
		try {
			return runner.query(sql, new ScalarHandler<T>(column), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> List<T> queryListColumn(String column, String sql, Object... params) {
		try {
			return runner.query(sql, new ColumnListHandler<T>(column), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Map<T, Map<String, Object>> queryKeyMap(String column, String sql, Object... params) {
		try {
			return runner.query(sql, new KeyedHandler<T>(column), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int update(String sql, Object... params) {
		try {
			return runner.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
