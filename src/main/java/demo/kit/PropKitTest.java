package demo.kit;

import java.io.File;

import org.junit.Test;

import kit4j.PathKit;
import kit4j.PropKit;

public class PropKitTest {
	
	@Test
	public void test1(){
		System.out.println(PropKit.load("log4j.properties").getProperty("log4j.rootLogger"));
		System.out.println(PropKit.load("log4j.properties", "iso-8859-1").getProperty("log4j.rootLogger"));
	}
	
	@Test
	public void test2(){
		System.out.println(PropKit.load(new File(PathKit.classPath()+"\\../classes/log4j.properties")).getProperty("log4j.rootLogger"));
	}
}
