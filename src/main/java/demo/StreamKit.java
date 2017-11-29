package demo;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamKit {

	/**
	 * default buffer size is 8192
	 */
	private static int DEFAULT_BUFFER_SIZE = 8192;

	public static void setDefaultBufferSize(int bufferSize) {
		DEFAULT_BUFFER_SIZE = bufferSize;
	}

	/**
	 * read bytes from InputStream
	 */
	public static byte[] read(InputStream in) {
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			write(in, result); close(result);
			return result.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * copy and close it
	 * @see #write(InputStream, OutputStream, int)
	 * @see #close(Closeable...)
	 */
	public static void writeAndClose(InputStream in, OutputStream out) {
		try { write(in, out); } 
		catch (Exception e) { throw new RuntimeException(e); } 
		finally { close(out, in); }
	}

	/**
	 * @see #write(InputStream, OutputStream, int)
	 */
	public static void write(InputStream in
			, OutputStream out) throws IOException {
		write(in, out, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * write InputStream byte to OutputStream
	 */
	public static void write(InputStream in
			, OutputStream out, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		for (int len = 0; (len = in.read(buffer)) != -1;) {
            out.write(buffer, 0, len);
        } out.flush();
	}

	/**
	 * close all streams scientific
	 * @see #close(Closeable)
	 */
	public static void close(Closeable... streams) {
		if(streams != null && streams.length > 0){
			for (Closeable stream : streams) close(stream);
		}
	}

	/**
	 * close stream scientific
	 */
	public static void close(Closeable stream) {
		if(stream != null){
			try { stream.close(); }
			catch (Exception e) { stream = null; }
		}
	}
}
