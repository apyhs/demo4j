package demo;

import java.io.File;

/**
 * path kit
 */
public class PathKit {

	/**
	 * file separator
	 */
	public static final String FSEP = System.getProperty("file.separator");

	/**
	 * path separator
	 */
	public static final String PSEP = System.getProperty("path.separator");

	public static String name(String path){
		return name(new File(path));
	}

	public static String name(File path){
		return path.getName();
	}

	public static String suffix(File path){
		return suffix(path.toString());
	}

	public static String suffix(String path){
		int i = path.lastIndexOf(".");
		return i == -1 ? "" : path.substring(i);
	}

	public static String parent(String path){
		return parent(new File(path));
	}

	public static String parent(File path){
		return path.getParent();
	}

	public static String abs(String path){
		return abs(new File(path));
	}

	public static String abs(File path){
		return path.getAbsolutePath();
	}

	public static String absAbs(String path){
		return absAbs(new File(path));
	}

	public static String absAbs(File path){
		try {
			return path.getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isAbs(String path) {
		return isAbs(new File(path));
	}

	public static boolean isAbs(File path) {
		return path.isAbsolute();
	}

	public static String subPath(String src, String del){
		if(StrKit.isBlank(src) || StrKit.isBlank(del))
			throw new IllegalArgumentException("src or del is blank!");
		if(!src.contains(del))
			throw new IllegalArgumentException("src not contain del!");
		return src.replace(del, "");
	}

	public static File subPath(File src, File del){
		String result = subPath(src.toString(), del.toString());
		return new File(result);
	}

	public static File subPath(File src, int beginIndex){
		String result = src.toString().substring(beginIndex);
		return new File(result);
	}

	public static File subPath(File src, int beginIndex, int endIndex){
		String result = src.toString().substring(beginIndex, endIndex);
		return new File(result);
	}

	public static File classPath() {
		try {
			ClassLoader classLoader = PathKit.class.getClassLoader();
			return new File(classLoader.getResource("").toURI().getPath());
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static File rootPath() {
		try {
			String path = PathKit.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile();
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static File clazzPath(Object obj) {
		return clazzPath(obj.getClass());
	}

	public static File clazzPath(Class<?> clazz) {
		try {
			return new File(clazz.getResource("").toURI().getPath());
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static File packagePath(Object obj) {
		return packagePath(obj.getClass());
	}

	public static File packagePath(Class<?> clazz) {
		Package p = clazz.getPackage();
		return new File(p != null ? p.getName().replaceAll("\\.", "/") : "");
	}

}
