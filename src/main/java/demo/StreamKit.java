package demo;

import java.io.*;
import java.nio.charset.Charset;

/**
 * stream kit
 */
public class StreamKit {
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

	public static String readAndClose(BufferedReader reader) {
		try { return read(reader); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(reader); }
	}

	public static String read(BufferedReader reader) throws IOException {
		StringBuilder result = new StringBuilder();
		boolean isFirst = true; String line = null;
		while ((line = reader.readLine()) != null) {
			if(isFirst) isFirst = false;
			else result.append(StrKit.LSEP);
			result.append(line);
		} return result.toString();
	}

	public static void writeAndClose(BufferedReader reader, BufferedWriter writer) {
		try { write(reader, writer); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(writer, reader); }
	}

	public static void write(BufferedReader reader, BufferedWriter writer) throws IOException {
		boolean isFirst = true; String line = null;
		while ((line = reader.readLine()) != null) {
			if(isFirst) isFirst = false;
			else writer.newLine();
			writer.write(line);
		} writer.flush();
	}

	public static BufferedWriter getBufferedWriter(OutputStream out) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(out));
	}

	public static BufferedWriter getBufferedWriter(OutputStream out, String charset) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(out, charset), defaultBufferSize);
	}

	public static BufferedWriter getBufferedWriter(OutputStream out, int bufferSize) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(out, defaultCharsetName), bufferSize);
	}

	public static BufferedWriter getBufferedWriter(OutputStream out, String charset, int bufferSize) throws IOException {
		return new BufferedWriter(new OutputStreamWriter(out, charset), bufferSize);
	}

	public static BufferedReader getBufferedReader(InputStream in) throws IOException {
		return new BufferedReader(new InputStreamReader(in, defaultCharsetName), defaultBufferSize);
	}

	public static BufferedReader getBufferedReader(InputStream in, String charset) throws IOException {
		return new BufferedReader(new InputStreamReader(in, charset), defaultBufferSize);
	}

	public static BufferedReader getBufferedReader(InputStream in, int bufferSize) throws IOException {
		return new BufferedReader(new InputStreamReader(in, defaultCharsetName), bufferSize);
	}

	public static BufferedReader getBufferedReader(InputStream in, String charset, int bufferSize) throws IOException {
		return new BufferedReader(new InputStreamReader(in, charset), bufferSize);
	}

	public static String readAndClose(Reader reader) {
		return readAndClose(reader, defaultBufferSize);
	}

	public static String readAndClose(Reader reader, int bufferSize) {
		try { return read(reader, bufferSize); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(reader); }
	}

	public static String read(Reader reader) throws IOException {
		return read(reader, defaultBufferSize);
	}

	public static String read(Reader reader, int bufferSize) throws IOException {
		return read(new BufferedReader(reader, bufferSize));
	}

	public static void writeAndClose(Reader reader, Writer writer) {
		writeAndClose(reader, writer, defaultBufferSize);
	}

	public static void writeAndClose(Reader reader, Writer writer, int bufferSize) {
		try { write(reader, writer, bufferSize); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(writer, reader); }
	}

	public static void write(Reader reader, Writer writer) throws IOException {
		write(reader, writer, defaultBufferSize);
	}

	public static void write(Reader reader, Writer writer, int bufferSize) throws IOException {
		write(new BufferedReader(reader, bufferSize), new BufferedWriter(writer, bufferSize));
	}

	public static byte[] readAndClose(InputStream in) {
		return readAndClose(in, defaultBufferSize);
	}

	public static byte[] readAndClose(InputStream in, int bufferSize) {
		try { return read(in, bufferSize); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(in); }
	}

	public static byte[] read(InputStream in) throws IOException {
		return read(in, defaultBufferSize);
	}

	public static byte[] read(InputStream in, int bufferSize) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		write(in, result, bufferSize); close(result);
		return result.toByteArray();
	}

	public static void writeAndClose(InputStream in, OutputStream out) {
		writeAndClose(in, out, defaultBufferSize);
	}

	public static void writeAndClose(InputStream in, OutputStream out, int bufferSize) {
		try { write(in, out, bufferSize); }
		catch (Exception e) { throw new RuntimeException(e); }
		finally { close(out, in); }
	}

	public static void write(InputStream in, OutputStream out) throws IOException {
		write(in, out, defaultBufferSize);
	}

	public static void write(InputStream in, OutputStream out, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		for (int len = 0; (len = in.read(buffer)) != -1;) {
			out.write(buffer, 0, len);
		} out.flush();
	}

	public static void close(Closeable... closeables) {
		if(closeables != null && closeables.length > 0){
			for (Closeable closeable : closeables) {
				close(closeable);
			}
		}
	}

	public static void close(Closeable closeable) {
		if(closeable != null){
			try {
				closeable.close();
			} catch (Exception e) {
				closeable = null;
				LogKit.error(e.getMessage(), e);
			}
		}
	}

	public static InputStream readFromBytes(byte[] data) {
		ByteArrayInputStream result = new ByteArrayInputStream(data);
		return result;
	}

	public static <T extends Serializable> byte[] serializeObject(T obj) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			serializeObject(obj, byteOut); return byteOut.toByteArray();
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static <T extends Serializable> void serializeObject(T obj, OutputStream out) throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(out);
		outputStream.writeObject(obj);
	}

	public static <T extends Serializable> T deserializeObject(byte[] data) {
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
			return deserializeObject(byteIn);
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T deserializeObject(InputStream in) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(in);
		return (T) inputStream.readObject();
	}

}
