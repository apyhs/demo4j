package demo;

public class ThreadKit {
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String currentMethodName(){
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	public static String currentClassName(){
		return Thread.currentThread().getStackTrace()[2].getClassName();
	}
	
	public static String currentFileName(){
		return Thread.currentThread().getStackTrace()[2].getFileName();
	}
	
	public static int currentLineNumber(){
		return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}
}
