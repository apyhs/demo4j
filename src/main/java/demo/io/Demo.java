package demo.io;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import kit4j.FileKit;
import kit4j.PathKit;

public class Demo {
	
	@Test
	public void test1(){
		FileKit.delete("D:\\Test\\111.xlsx");
	}
	
	@Test
	public void test2(){
		System.out.println(PathKit.subPath("D:\\Test\\111.xlsx", "D:\\Test"));;
		System.out.println(FileKit.rename(new File("D:\\Test\\111.xlsx"), new File("D:\\Test\\222.xlsx")));;
	}
	
	@Test
	public void test3() throws IOException{
//		File f1 = null;
//		File f2 = null;
//		PathKit.subPath(f1, f2);
//		Object obj = null;
//		obj.getClass();
//		File path = null;
//		path.getCanonicalPath();
	}
}
