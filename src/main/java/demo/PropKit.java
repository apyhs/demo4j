package demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropKit {
	
	/**
	 * subpath in class path
	 */
	public static Properties load(String subpathInClassPath) {
		try {
			InputStream in = FileKit.read(subpathInClassPath);
			if (in == null)
				throw new RuntimeException("resource can not be found!");
			return load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * subpath in class path
	 */
	public static Properties load(String subpathInClassPath, String charset) {
		try {
			InputStream in = FileKit.read(subpathInClassPath);
			if (in == null) 
				throw new RuntimeException("resource can not be found!");
			return load(in, charset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Properties load(File file) {
		try {
			return load(FileKit.read(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Properties load(File file, String charset) {
		try {
			return load(FileKit.read(file), charset);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Properties load(InputStream in, String charset) throws IOException {
		if(StrKit.notBlank(charset)) {
			Properties prop = new Properties();
			InputStreamReader reader = new InputStreamReader(in, charset);
			prop.load(reader); StreamKit.close(reader);
			return prop;
		} else { return load(in); }
	}
	
	public static Properties load(InputStream in) throws IOException {
		Properties prop = new Properties();
		InputStreamReader reader = new InputStreamReader(in);
		prop.load(reader); StreamKit.close(reader);
		return prop;
	}
}
