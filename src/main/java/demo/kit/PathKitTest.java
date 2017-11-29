package demo.kit;

import java.io.File;

import org.junit.Test;

import kit4j.PathKit;

public class PathKitTest {

	@Test
	public void a0(){
		System.out.println(new File("aa/ss\\..\\da/sds.doc"));
		System.out.println(PathKit.abs("aa/ss\\..\\da/sds.doc"));
		System.out.println(PathKit.abs("/home/aa"));
		System.out.println(PathKit.abs("../home/aa"));
		System.out.println(PathKit.abs0("c:\\window\\aa"));
		System.out.println((PathKit.abs("c:\\window\\aa")).equals("c:\\window\\aa"));
	}
	
	@Test
	public void a1(){
//		String s1 = "C:/window\\..\\aa/bb/ss";
		String s1 = "/window\\aa/bb/ss";
		System.out.println(PathKit.isAbs(s1));
		String p1 = new File(s1).toString();
		String p2 = PathKit.abs0(new File(s1));
		System.out.println(p1+"\n"+p2);
		System.out.println(p1.equals(p2));
	}
	
	@Test
	public void a2() throws Exception{
		System.out.println(PathKitTest.class.getResource("/"));
		System.out.println(PathKitTest.class.getResource("/").getFile());
		String path = PathKitTest.class.getResource("/").toURI().getPath();
		System.out.println(path);
		System.out.println(new File(path).getParentFile().getParentFile().getCanonicalPath());
	}
	
	@Test
	public void a3() {
		String rootClassPath = null;
		if (rootClassPath == null) {
			try {
				String path = PathKit.class.getClassLoader().getResource("").toURI().getPath();
				rootClassPath = new File(path).getAbsolutePath();
				System.out.println(PathKit.class.getClassLoader().getResource(""));
			}
			catch (Exception e) {
				String path = PathKit.class.getClassLoader().getResource("").getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		}
		System.out.println(rootClassPath);
	}
	
	@Test
	public void a4() {
		System.out.println(PathKitTest.class.getResource("/").getFile());
		System.out.println(PathKitTest.class.getResource("").getFile());
		Package p = this.getClass().getPackage();
		System.out.println(p != null ? p.getName().replaceAll("\\.", "/") : "");
	}
}
