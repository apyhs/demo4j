package demo.function.inetlocation;

import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class InetQuery {

	@Test
	public void test() throws Exception {
		String dbPath = "e:\\ip.mmdb";
		String ip = "127.0.0.1";
		System.out.println("该IP的物理地址为：" + new InetLocation(ip, new File(dbPath), "zh-CN"));
	}

	public static void main(String[] args) throws Exception {
		 @SuppressWarnings("resource")
		 Scanner scanner = new Scanner(System.in);
		 System.out.print("请输入IP数据库路径（空则使用当前路径下的ip.mmdb文件）：");
		 String dbPath = scanner.nextLine();
		 dbPath = (dbPath != null) && (dbPath.trim().length() > 0) ? dbPath : "ip.mmdb";
		 System.out.println("IP数据库路径为：" + dbPath);
		 System.out.println();
		 for(;;){
			 System.out.print("请输入IP：");
			 String ip = scanner.nextLine();
			 System.out.println("该IP的物理地址为：" + new InetLocation(ip, new File(dbPath), "zh-CN"));
			 System.out.println();
		 }
	}

}
