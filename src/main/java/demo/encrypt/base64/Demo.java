package demo.encrypt.base64;

import kit4j.CodeKit;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;


public class Demo {
	
	@Test
	public void testDemo(){
		System.out.println(new String(Base64.encodeBase64("%&SDGS=-:||>?DG6789".getBytes())));
		System.out.println(new String(Base64.decodeBase64("JSZTREdTPS06fHw+P0RHNjc4OQ==")));
		
		System.out.println();
		
		System.out.println(Base64.encodeBase64String("%&SDGS=-:||>?DG6789".getBytes()));
		System.out.println(new String(Base64.decodeBase64("JSZTREdTPS06fHw+P0RHNjc4OQ==")));
		
		System.out.println();
		
		System.out.println(new String(Base64.encodeBase64("%&SDGS=-:||>?DG6789".getBytes(), false, true)));
		System.out.println(new String(Base64.encodeBase64URLSafe("%&SDGS=-:||>?DG6789".getBytes())));
		System.out.println(Base64.encodeBase64URLSafeString("%&SDGS=-:||>?DG6789".getBytes()));
	}
	
	@Test
	public void t1(){
		System.out.println(CodeKit.encodeBase64("%&SDGS=-:||>?DG6789".getBytes()));;
	}
}
