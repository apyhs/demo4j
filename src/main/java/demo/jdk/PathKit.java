package demo.jdk;

import java.io.File;
import java.net.URISyntaxException;

public class PathKit {

    public static final String FSEP = System.getProperty("file.separator");

    public static final String PSEP = System.getProperty("path.separator");

    public static String abs(File path){
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

    public static File classPath() throws URISyntaxException {
        if (classPath == null) {
            ClassLoader classLoader = PathKit.class.getClassLoader();
            classPath = new File(classLoader.getResource("").toURI().getPath());
            return classPath;
        } else {
            return classPath;
        }
    }

    public static File rootPath() throws URISyntaxException {
        if (rootPath == null) {
            String path = PathKit.class.getResource("/").toURI().getPath();
            rootPath = new File(path).getParentFile().getParentFile();
            return rootPath;
        } else {
            return rootPath;
        }
    }

    public static File clazzPath(Class<?> clazz) throws URISyntaxException {
        return new File(clazz.getResource("").toURI().getPath());
    }

    public static File packagePath(Class<?> clazz) {
        Package p = clazz.getPackage();
        return new File(p != null ? p.getName().replaceAll("\\.", "/") : "");
    }

    private static File rootPath = null;
    private static File classPath = null;

}
