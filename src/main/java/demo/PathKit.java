package demo;

import java.io.File;

public class PathKit {
	
	/**
	 * file separator
	 */
	public static final String FSEP = System.getProperty("file.separator");
	
	/**
	 * path separator
	 */
	public static final String PSEP = System.getProperty("path.separator");
	
	/**
	 * get class path
	 */
	public static String classPath() {
		try {
			ClassLoader classLoader = PathKit.class.getClassLoader();
			return abs(classLoader.getResource("").toURI().getPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * get root path 
	 */
	public static String rootPath() {
		try {
			String path = PathKit.class.getResource("/").toURI().getPath();
			return abs(new File(path).getParentFile().getParentFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * get obj package path
	 */
	public static String packagePath(Object obj) {
		return packagePath(obj.getClass());
	}
	
	/**
	 * get clazz package path
	 */
	public static String packagePath(Class<?> clazz) {
		Package p = clazz.getPackage();
		return p != null ? p.getName().replaceAll("\\.", FSEP) : "";
	}

	/**
	 * @see #subPath(String, String)
	 */
	public static String subPath(File src, File del){
		return subPath(src.toString(), del.toString());
	}
	
	/**
	 * src contain del, so src subtract del
	 */
	public static String subPath(String src, String del){
		if(StrKit.isBlank(src) || StrKit.isBlank(del))
			throw new RuntimeException("src or del is blank!");
		if(!src.contains(del))
			throw new RuntimeException("src not contain del!");
		return src.replace(del, "");
	}
	
	/**
	 * get obj Class path
	 * @see #path(Class)
	 */
	public static String path(Object obj) {
		return path(obj.getClass());
	}
	
	/**
	 * get clazz path
	 */
	public static String path(Class<?> clazz) {
		try {
			return abs(clazz.getResource("").toURI().getPath());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @see #isAbs(File)
	 */
	public static boolean isAbs(String path) {
		return isAbs(new File(path));
	}
	
	/**
	 * judge path is absolute path
	 */
	public static boolean isAbs(File path) {
		return StrKit.equals(path.toString(), abs0(path));
	}
	
	/**
	 * @see #abs(File)
	 */
	public static String abs(String path){
		return abs(new File(path));
	}
	
	/**
	 * get canonical absolute path
	 * @see #abs0(File)
	 */
	public static String abs(File path){
		try {
			return path.getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @see #abs0(File)
	 */
	public static String abs0(String path){
		return abs0(new File(path));
	}
	
	/**
	 * get non canonical absolute path
	 */
	public static String abs0(File path){
		return path.getAbsolutePath();
	}
	
	/**
	 * @see #suffix(String)
	 */
	public static String suffix(File path){
		return suffix(path.toString());
	}
	
	/**
	 * get suffix from path
	 */
	public static String suffix(String path){
		return path.substring(path.lastIndexOf("."));
	}
	
	/**
	 * @see #parent(File)
	 */
	public static String parent(String path){
		return parent(new File(path));
	}
	
	/**
	 * get parent path on path 
	 */
	public static String parent(File path){
		return path.getParent();
	}
	
	/**
	 * @see #name(File)
	 */
	public static String name(String path){
		return name(new File(path));
	}
	
	/**
	 * get name (file or directory name) on path 
	 */
	public static String name(File path){
		return path.getName();
	}
}
