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
import java.nio.charset.Charset;
import java.util.LinkedList;

/**
 * file kit
 */
public class FileKit {
	private static String defaultCharsetName = Charset.defaultCharset().name();
	private static int defaultBufferSize = 8192;

	public static void setDefCharsetName(String charset){
		if (StrKit.isBlank(charset)) {
			throw new IllegalArgumentException("charset name can not be blank!");
		} defaultCharsetName = charset;
	}

	public static void setDefBufferSize(int bufferSize) {
		if (bufferSize == 0) {
			throw new IllegalArgumentException("buffer size can not be zero!");
		} defaultBufferSize = bufferSize;
	}

	public static void move(String src, String dest){
		copy(src, dest); delete(src);
	}

	public static void move(File src, File dest){
		copy(src, dest); delete(src);
	}

	public static void copy(String src, String dest){
		copy(new File(src), new File(dest));
	}

	public static void copy(File src, File dest) {
		copy(src, dest, false);
	}

	/**
	 * copy dir/src/ dir/dest/ == copy dir/src/* dir/dest/
	 * @param overwrite null:skip, false:save as, true:overwrite;
	 */
	public static void copy(File src, File dest, Boolean overwrite) {
		if (!src.exists()) {
			throw new IllegalArgumentException("src not exist!");
		} else if (src.isDirectory()) {
			if (!dest.exists() && !make(dest)) {
				throw new RuntimeException("make dest dir fail!");
			} LinkedList<File> fileList = new LinkedList<>();
			fileList.add(src);
			while (!fileList.isEmpty()) {
				File[] files = fileList.removeFirst().listFiles();
				if (files == null || files.length == 0) continue;
				for (File file : files) {
					String subPath = PathKit.subPath(file, src).toString();
					File destPath = new File(dest, subPath);
					if (file.isDirectory()) {
						if (!destPath.exists()) make(destPath);
						fileList.addFirst(file);
					} else { copy(file, destPath, overwrite); }
				}
			}
		} else if (src.isFile()) {
			File destParent = new File(PathKit.parent(dest));
			if (!destParent.exists() && !make(destParent)) {
				throw new RuntimeException("dest parent dir make fail!");
			}
			if (dest.exists() && dest.isFile()) {
				if (overwrite != null) {
					if (overwrite) { write2File(src, dest, false); }
					else {
						String suffix = PathKit.suffix(dest);
						String name = PathKit.name(dest);
						File newDest = null;
						do { String newName = name + "_" + TimeKit.tsl();
							newDest = new File(destParent, newName + suffix);
						} while (newDest.exists());
						create(newDest); write2File(src, newDest);
					}
				} else { /* no processing */ }
			} else { create(dest); write2File(src, dest); }
		}
	}

	public static void delete(String dest) {
		delete(new File(dest));
	}

	public static void delete(File dest) {
		if (dest != null && dest.exists()) {
			if (dest.isDirectory()) {
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
							if (file.isDirectory()) {
								if (!addCurrent) {
									fileList.addFirst(current);
									addCurrent = true;
								}
								fileList.addFirst(file);
							} else file.delete();
						}
					}
					if (!addCurrent) current.delete();
				}
			} else {
				dest.delete();
			}
		}
	}

	public static String rename(String src, String newName) {
		return rename(new File(src), newName);
	}

	public static String rename(File src, String newName) {
		File dest = new File(PathKit.parent(src), newName);
		return rename(src, dest) ? dest.toString() : src.toString();
	}

	public static boolean rename(File src, File dest) {
		return src.renameTo(dest);
	}

	public static boolean make(String dirPath) {
		return new File(dirPath).mkdirs();
	}

	public static boolean make(File dirPath) {
		return dirPath.mkdirs();
	}

	public static boolean create(String filePath) {
		return create(new File(filePath));
	}

	public static boolean create(File filePath) {
		try { return filePath.createNewFile(); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static void write2File(String data, String dest) {
		write2File(data, new File(dest), false, defaultCharsetName);
	}

	public static void write2File(String data, File dest) {
		write2File(data, dest, false, defaultCharsetName);
	}

	public static void write2File(String data, File dest, boolean append) {
		write2File(data, dest, append, defaultCharsetName);
	}

	public static void write2File(String data, File dest, String charset) {
		write2File(data, dest, false, charset);
	}

	public static void write2File(String data, File dest, boolean append, String charset) {
		if(StrKit.notBlank(data)) {
			try {
				BufferedWriter writer = getBufferedWriter(dest, append, charset);
				writer.write(data); StreamKit.close(writer);
			} catch (Exception e) { throw new RuntimeException(e); }
		}
	}

	public static void write2File(byte[] data, String dest) {
		write2File(data, new File(dest), false);
	}

	public static void write2File(byte[] data, File dest) {
		write2File(data, dest, false);
	}

	public static void write2File(byte[] data, File dest, boolean append) {
		if(data != null && data.length != 0) {
			try {
				OutputStream out = getFileOutputStream(dest, append);
				out.write(data); StreamKit.close(out);
			} catch (Exception e) { throw new RuntimeException(e); }
		}
	}

	public static void write2File(File src, File dest) {
		write2File(src, dest, false);
	}

	public static void write2File(File src, File dest, boolean append) {
		try { StreamKit.writeAndClose(getFileInputStream(src), getFileOutputStream(dest, append)); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static String read2String(File dest) {
		return read2String(dest, defaultCharsetName);
	}

	public static String read2String(File dest, String charset) {
		try { return StreamKit.readAndClose(getBufferedReader(dest, charset)); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static byte[] read2Bytes(File dest) {
		try { return StreamKit.readAndClose(getFileInputStream(dest)); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static BufferedWriter getBufferedWriter(String dest) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest), defaultCharsetName), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(File dest) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest, false), defaultCharsetName), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(File dest, boolean append) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest, append), defaultCharsetName), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(File dest, String charset) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest, false), charset), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(File dest, boolean append, String charset) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest, append), charset), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(File dest, boolean append, String charset, int bufferSize) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(getFileOutputStream(dest, append), charset), bufferSize);
	}

	public static FileOutputStream getFileOutputStream(String dest) throws IOException {
		return new FileOutputStream(dest);
	}

	public static FileOutputStream getFileOutputStream(File dest) throws IOException {
		return new FileOutputStream(dest);
	}

	public static FileOutputStream getFileOutputStream(File dest, boolean append) throws IOException {
		return new FileOutputStream(dest, append);
	}

	public static BufferedReader getBufferedReader(String dest) throws IOException {
		return new BufferedReader(new InputStreamReader(getFileInputStream(dest), defaultCharsetName), defaultBufferSize);
	}

	public static BufferedReader getBufferedReader(File dest) throws IOException {
		return new BufferedReader(new InputStreamReader(getFileInputStream(dest), defaultCharsetName), defaultBufferSize);
	}

	public static BufferedReader getBufferedReader(File dest, int bufferSize) throws IOException {
		return new BufferedReader(new InputStreamReader(getFileInputStream(dest), defaultCharsetName), bufferSize);
	}

	public static BufferedReader getBufferedReader(File dest, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(getFileInputStream(dest), charset), defaultBufferSize);
	}

	public static BufferedReader getBufferedReader(File dest, String charset, int bufferSize) throws IOException {
		return new BufferedReader(new InputStreamReader(getFileInputStream(dest), charset), bufferSize);
	}

	public static FileInputStream getFileInputStream(String dest) throws IOException {
		return new FileInputStream(dest);
	}

	public static FileInputStream getFileInputStream(File dest) throws IOException {
		return new FileInputStream(dest);
	}

	public static InputStream getClassPathInputStream(String name) {
		// name is filename or subpath in class path
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
	}

}
