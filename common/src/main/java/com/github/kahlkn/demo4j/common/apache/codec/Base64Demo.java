package com.github.kahlkn.demo4j.common.apache.codec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.Arrays;

public class Base64Demo {

	@Test
	public void testToBase64() {
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
	public void test1IsBase64() {
		System.out.println(Base64.isBase64("JSZTREdTPS06fHw+P0RHNjc4OQ=="));
		System.out.println(Base64.isBase64("1245646asdfas"));
		System.out.println(Arrays.toString(Base64.decodeBase64("1245646asdfas")));
	}

}
