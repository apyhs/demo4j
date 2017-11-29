package demo.jdk;

import java.io.*;

public class StreamKit {

    public static Reader make(String data) {
        return new StringReader(data);
    }

    public static InputStream make(byte[] data) {
        return new ByteArrayInputStream(data);
    }

    public static String readAndClose(BufferedReader reader) throws IOException {
        try { return read(reader); }
        finally { close(reader); }
    }

    public static String read(BufferedReader reader) throws IOException {
        StringWriter result = new StringWriter();
        BufferedWriter writer = new BufferedWriter(result);
        write(reader, writer);
        return result.toString();
    }

    public static void writeAndClose(BufferedReader reader, BufferedWriter writer) throws IOException {
        try { write(reader, writer); }
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

    public static String readAndClose(Reader reader) throws IOException {
        return readAndClose(reader, defaultBufferSize);
    }

    public static String readAndClose(Reader reader, int bufferSize) throws IOException {
        try { return read(reader, bufferSize); }
        finally { close(reader); }
    }

    public static String read(Reader reader) throws IOException {
        return read(reader, defaultBufferSize);
    }

    public static String read(Reader reader, int bufferSize) throws IOException {
        return read(new BufferedReader(reader, bufferSize));
    }

    public static void writeAndClose(Reader reader, Writer writer) throws IOException {
        writeAndClose(reader, writer, defaultBufferSize);
    }

    public static void writeAndClose(Reader reader, Writer writer, int bufferSize) throws IOException {
        try { write(reader, writer, bufferSize); }
        finally { close(writer, reader); }
    }

    public static void write(Reader reader, Writer writer) throws IOException {
        write(reader, writer, defaultBufferSize);
    }

    public static void write(Reader reader, Writer writer, int bufferSize) throws IOException {
        BufferedReader r = new BufferedReader(reader, bufferSize);
        BufferedWriter w = new BufferedWriter(writer, bufferSize);
        write(r, w);
    }

    public static byte[] readAndClose(InputStream in) throws IOException {
        return readAndClose(in, defaultBufferSize);
    }

    public static byte[] readAndClose(InputStream in, int bufferSize) throws IOException {
        try { return read(in, bufferSize); }
        finally { close(in); }
    }

    public static byte[] read(InputStream in) throws IOException {
        return read(in, defaultBufferSize);
    }

    public static byte[] read(InputStream in, int bufferSize) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        write(in, result, bufferSize);
        return result.toByteArray();
    }

    public static void writeAndClose(InputStream in, OutputStream out) throws IOException {
        writeAndClose(in, out, defaultBufferSize);
    }

    public static void writeAndClose(InputStream in, OutputStream out, int bufferSize) throws IOException {
        try { write(in, out, bufferSize); }
        finally { close(out, in); }
    }

    public static void write(InputStream in, OutputStream out) throws IOException {
        write(in, out, defaultBufferSize);
    }

    public static void write(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        for (int len; (len = in.read(buffer)) != -1;) {
            out.write(buffer, 0, len);
        } out.flush();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        return deserialize(in);
    }

    public static Object deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(in);
        return inputStream.readObject();
    }

    public static <T extends Serializable> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        serialize(obj, out); return out.toByteArray();
    }

    public static <T extends Serializable> void serialize(T obj, OutputStream out) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
        outputStream.writeObject(obj);
    }

    public static void close(Closeable... cs) {
        if (cs != null && cs.length > 0) {
            for (Closeable c : cs) close(c);
        }
    }

    public static void close(Closeable c) {
        try { if(c != null) c.close(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private static final int defaultBufferSize = 8192;

}
