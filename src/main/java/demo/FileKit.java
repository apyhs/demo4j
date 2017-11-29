package demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class FileKit {
	
	private static boolean DEFAULT_IS_APPEND = false;
	private static String DEFAULT_CHARSET_NAME = "UTF-8";

	public static void setDefaultIsAppend(boolean isAppend){
		DEFAULT_IS_APPEND = isAppend;
	}

	public static void setDefaultCharsetName(String charset){
		if (StrKit.isBlank(charset)) {
			throw new IllegalArgumentException("charset can not be blank!");
		}
		DEFAULT_CHARSET_NAME = charset;
	}

	public static boolean make(File dirPath){
		return dirPath.mkdirs();
	}

	public static boolean create(File filePath){
		try {
			return filePath.createNewFile();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write2File(String data, File dest){
		write2File(data, dest, DEFAULT_IS_APPEND);
	}
	
	public static void write2File(String data, File dest, boolean append){
		write2File(data, DEFAULT_CHARSET_NAME, dest, append);
	}
	
	public static void write2File(String data, String charset, File dest){
		write2File(data, charset, dest, DEFAULT_IS_APPEND);
	}
	
	public static void write2File(String data
			, String charset, File dest, boolean append){
		if(StrKit.isBlank(data))
			throw new RuntimeException("data is blank!");
		try {
			BufferedWriter writer = write(dest, charset, append);
			writer.write(data);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write2File(byte[] data, File path){
		write2File(data, path, DEFAULT_IS_APPEND);
	}
	
	public static void write2File(byte[] data, File path, boolean append){
		try {
			OutputStream out = write(path, append);
			out.write(data);
			StreamKit.close(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write2File(File src, File dest){
		write2File(src, dest, DEFAULT_IS_APPEND);
	}
	
	public static void write2File(File src, File dest, boolean append){
		try {
			StreamKit.writeAndClose(read(src), write(dest, append));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static BufferedWriter write(File dest, String charset, boolean append) throws IOException{
		return new BufferedWriter(new OutputStreamWriter(write(dest, append), charset));
	}
	
	public static FileOutputStream write(File dest, boolean append) throws IOException{
		return new FileOutputStream(dest, append);
	}
	
	public static String read2String(File path){
		return read2String(path, DEFAULT_CHARSET_NAME);
	}
	
	public static String read2String(File path, String charset) {
		try {
			StringBuffer result = new StringBuffer();
			BufferedReader reader = read(path, charset);
			for(String line = null; (line = reader.readLine()) != null;){
				result.append(line);
			}
			reader.close();
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] read2Bytes(File path){
		try {
			return StreamKit.read(read(path));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * subpath in class path
	 */
	public static InputStream read(String subpathInClassPath) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(subpathInClassPath);
	}
	
	public static BufferedReader read(File path, String charset) throws IOException{
		return new BufferedReader(new InputStreamReader(read(path), charset));
	}
	
	public static FileInputStream read(File path) throws IOException{
		return new FileInputStream(path);
	}
	
	public static void move(String src, String dest){
		move(new File(src), new File(dest));
	}
	
	public static void move(File src, File dest){
		copy(src, dest);
		delete(src);
	}
	
	public static void copy(String src, String dest){
		copy(new File(src), new File(dest));
	}
	
	public static void copy(File src, File dest){
		if(!src.exists()) throw new RuntimeException("src not exist!");
		if(dest.exists()) throw new RuntimeException("dest already exist!");
		if(src.isDirectory()) {
			if(!make(dest)) throw new RuntimeException("make dest dir fail!");
			LinkedList<File> fileList = new LinkedList<>();
			fileList.add(src);
			while (!fileList.isEmpty()) {
				File[] files = fileList.removeFirst().listFiles();
				if (files == null || files.length == 0) continue;
				for (File file : files) {
					if(file.isDirectory()){
						make(new File(dest, PathKit.subPath(file, src)));
						fileList.addFirst(file);
					}else{
						copy(file, new File(dest, PathKit.subPath(file, src)));
					}
				}
			}
		}else{
			File destParent = new File(PathKit.parent(dest));
			if(!destParent.exists() && !make(destParent))
				throw new RuntimeException("dest parent dir make fail!");
			create(dest);
			write2File(src, dest, false);
		}
	}
	
	/**
	 * @see #delete(File)
	 */
	public static void delete(String dest) {
		delete(new File(dest));
	}
	
	/**
	 * delete a file or directory
	 */
	public static void delete(File dest) {
		if(!dest.exists())
			throw new RuntimeException("dest not exist!");
		if(dest.isDirectory()){
			// stack model
			LinkedList<File> fileList = new LinkedList<>();
	        fileList.addFirst(dest);
	        while (!fileList.isEmpty()) {
	        	File current = fileList.removeFirst();
	            File[] files = current.listFiles();
	            // is add current ?
	            boolean addCurrent = false;
	            if (files != null && files.length > 0) {
	                for (File file : files) {
	                    if (file.isDirectory()){
	                    	if(!addCurrent){
	                    		fileList.addFirst(current);
	                    		addCurrent = true;
	                    	}
	                        fileList.addFirst(file);
	                    }
	                    else file.delete();
	                }
	            }
	            if(!addCurrent) current.delete();
	        }
		}else{
			dest.delete();
		}
	}
	
	/**
	 * @see #rename(File, String)
	 */
	public static String rename(String src, String newName){
		return rename(new File(src), newName);
	}
	
	/**
	 * rename src file or src directory to newName
	 * @param src is a path from file or directory
	 * @param newName just a name, if file, contain suffix best
	 */
	public static String rename(File src, String newName){
		File dest = new File(PathKit.parent(src), newName);
		return rename(src, dest) ? dest.toString() : null;
	}
	
	/**
	 * rename src path to dest path
	 */
	public static boolean rename(File src, File dest){
		if (src == null) throw new NullPointerException();
		return src.renameTo(dest);
	}
}
