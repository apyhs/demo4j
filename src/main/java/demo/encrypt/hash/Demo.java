package demo.encrypt.hash;

import org.junit.Test;

import kit4j.HashKit;

public class Demo {
	
	@Test
	public void testSha1(){
		String u="111", p="222";
		String sha1 = HashKit.sha1(u+"222"+p);
		System.out.println(sha1);
	}
}
