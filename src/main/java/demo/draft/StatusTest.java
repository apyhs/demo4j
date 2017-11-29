package demo.draft;

import org.junit.Test;

import kit4j.PathKit;
import kit4j.builder.StatusCode;

public class StatusTest {
	
	@Test
	public void test1(){
		String jsonPath = PathKit.path(StatusCode.class)+"\\"+"code.json";
		String packageName = "me.anmt.demo.draft";
		String destPath = PathKit.rootPath() + "\\src\\main\\java\\" + packageName.replaceAll("\\.", "\\");
		StatusCode.make(jsonPath, "utf-8", packageName, "CodeTest", destPath, "utf-8");
	}
	
	@Test
	public void test2(){
		System.out.println(PathKit.classPath());
		System.out.println(PathKit.rootPath());
	}
}
