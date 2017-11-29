package demo.base;

import java.io.File;
import java.net.URL;

public class PathUtils {

    public static final String FSEP = System.getProperty("file.separator");
    public static final String PSEP = System.getProperty("path.separator");
    public static final File ROOT_PATH = rootPath();
    public static final File CLASS_PATH = classPath();

    public static String abs(File path) {
        try {
            return path.getCanonicalPath();
        } catch (Exception e) {
            return path.getAbsolutePath();
        }
    }

    public static boolean isAbs(File path) {
        return path.isAbsolute();
    }

    public static String name(File path) {
        return path.getName();
    }

    public static String suffix(String path) {
        int i = path.lastIndexOf(".");
        return i == -1 ? "" : path.substring(i);
    }

    public static String parent(File path) {
        return path.getParent();
    }

    public static File classPath() {
        try {
            ClassLoader classLoader = PathUtils.class.getClassLoader();
            URL res = classLoader.getResource("");
            return res != null ? new File(res.toURI().getPath()) : null;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static File rootPath() {
        try {
            String path = PathUtils.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static File clazzPath(Class<?> clazz) {
        try {
            return new File(clazz.getResource("").toURI().getPath());
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static File packagePath(Class<?> clazz) {
        Package p = clazz.getPackage();
        return new File(p != null ? p.getName().replaceAll("\\.", "/") : "");
    }

}
