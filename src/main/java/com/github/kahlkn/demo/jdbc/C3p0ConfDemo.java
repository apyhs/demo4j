package com.github.kahlkn.demo.jdbc;

import com.apyhs.artoria.util.ThreadUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;


public class C3p0ConfDemo {
	
	@Test
	public void t1() throws Exception{
		DbConf dataSource = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root",
				"root");
		dataSource.init();
		Connection conn = dataSource.getDataSource().getConnection();
		ResultSet resultSet = conn.createStatement().executeQuery("select * from tb_user;");
		while (resultSet.next()) {
			System.out.print(resultSet.getString(1)+" ");
			System.out.print(resultSet.getString(2)+" ");
			System.out.print(resultSet.getString(3)+" ");
			System.out.println(resultSet.getString(4));
		}
		resultSet.close();
		conn.close();
	}
	
	@Test
	public void t2() throws Exception{
		DbConf dataSource = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root",
				"root");
		dataSource.init();
		ComboPooledDataSource comboPooledDataSource = ((C3p0Conf)dataSource).getComboPooledDataSource();
		showPoolInfo(comboPooledDataSource);
		Connection connection = comboPooledDataSource.getConnection();
		ThreadUtils.sleepQuietly(5000);
		showPoolInfo(comboPooledDataSource);
		connection.close();
		showPoolInfo(comboPooledDataSource);
		for (int i = 0; i < 10; i++) {
			ThreadUtils.sleepQuietly(1000);
			showPoolInfo(comboPooledDataSource);
		}
	}
	
	@Test
	public void t3() throws Exception{
		DbConf ds = new C3p0Conf(
				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
				"root",
				"root");
		ds.init();
		DataSource dataSource = ds.getDataSource();
		showPoolInfo((ComboPooledDataSource)dataSource);
		Connection connection = dataSource.getConnection();
		ThreadUtils.sleepQuietly(5000);
		showPoolInfo((ComboPooledDataSource)dataSource);
		connection.close();
		showPoolInfo((ComboPooledDataSource)dataSource);
		for (int i = 0; i < 10; i++) {
			ThreadUtils.sleepQuietly(1000);
			showPoolInfo((ComboPooledDataSource)dataSource);
		}
	}
	
	private static void showPoolInfo(ComboPooledDataSource pool) {
        PooledDataSource pds = (PooledDataSource) pool;
        if(null != pds){
            try {
                System.out.println("------------c3p0连接池链接状态--------------");
                System.out.println("c3p0连接池中 【 总共 】 连接数量："+pds.getNumConnectionsDefaultUser());
                System.out.println("c3p0连接池中 【  忙  】 连接数量："+pds.getNumBusyConnectionsDefaultUser());
                System.out.println("c3p0连接池中 【 空闲 】 连接数量："+pds.getNumIdleConnectionsDefaultUser());
                System.out.println("c3p0连接池中 【未关闭】 连接数量："+pds.getNumUnclosedOrphanedConnectionsAllUsers());
            } catch (Exception e) {
                System.out.println("c3p0连接池异常！"); 
            }
        }
    }    
}
