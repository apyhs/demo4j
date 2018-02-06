package com.apyhs.demo.jdk;

import org.junit.Test;

public class ThreadDemo {

	@Test
	public void testStackTrace() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : elements) {
			System.out.println(element.getMethodName());
			System.out.println(element.getClassName());
			System.out.println(element.getFileName());
			System.out.println(element.getLineNumber());
			System.out.println();
		}
	}

	@Test
	public void testGc() {
		System.gc();
	}

	@Test
	public void testCurrent() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName());
		System.out.println(Thread.currentThread().getStackTrace()[1].getFileName());
		System.out.println(Thread.currentThread().getStackTrace()[1].getLineNumber());
	}

	@Test
	public void testSleep() {
		try {
			System.out.println("start!");
			Thread.sleep(1000);
			System.out.println("end!");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
