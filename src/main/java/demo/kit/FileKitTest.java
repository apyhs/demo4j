package demo.kit;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import kit4j.FileKit;

public class FileKitTest {
	
	@Test
	public void copyTest(){
		FileKit.copy(new File("D:\\Test\\a1.xlsx"), new File("D:\\Test\\a1.xlsx"));
//		FileKit.copy(new File("D:\\Test\\a"), new File("D:\\Test\\e"));
	}
	
	@Test
	public void test1(){
//		File file = new File("D:\\T\\A\\B");
		File file = new File("D:\\Test");
		System.out.println(file.exists());
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException{
		String read = FileKit.read2String(new File("C:\\Users\\admin\\Desktop\\流程.txt"), "iso-8859-1");
		System.out.println(new String(read.getBytes("iso-8859-1"), "gb2312"));
	}
	
	@Test
	public void test3(){
//		FileKit.move(new File("D:\\Test\\f"), new File("D:\\Test\\z"));
//		FileKit.move(new File("D:\\Test\\a1.xlsx"), new File("D:\\Test\\a2222.xlsx"));
		System.out.println(FileKit.rename(new File("D:\\Test\\z"), new File("D:\\Test\\f")));
	}
}
