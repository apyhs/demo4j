package demo.encrypt.key;

import kit4j.CodeKit;
import kit4j.EncrKit;
import kit4j.StrKit;
import org.junit.Test;

public class Demo {
	
	@Test
	public void t1(){
		String list = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 100; i++) {
			System.out.println(EncrKit.generateSeq(list.toCharArray(), 100));
		}
	}
	
	@Test
	public void t2(){
		String list = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < 100; i++) {
			System.out.println(StrKit.randomSort(list));
		}
	}
	
	@Test
	public void t3(){
		String src = "IW7VJWI5DT86T555A4YX4MDKA1UKGWBZOXCA4RY5V6UT810OVM6JXQOGUJVIMX77PAZMRLHHSQ1T4ORCV90MOVK9HD85SA6GY32X";
		System.out.println(src);
		System.out.println(StrKit.removeRepeat(src));
	}
	
}
