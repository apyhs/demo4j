package demo.jdk;

import java.io.*;
import java.util.LinkedList;

public class FileKit {

    public static boolean mkdirs(String dirPath) {
        return mkdirs(new File(dirPath));
    }

    public static boolean mkdirs(File dirPath) {
        return dirPath.mkdirs();
    }

    public static boolean create(String filePath) throws IOException {
        return create(new File(filePath));
    }

    public static boolean create(File filePath) throws IOException {
        return filePath.createNewFile();
    }

    public static void delete(String dest) {
        delete(new File(dest));
    }

    public static void delete(File dest) {
        if (dest == null || !dest.exists()) return;
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
                        } else {
                            file.delete();
                        }
                    }
                }
                if (!addCurrent) current.delete();
            }
        } else {
            dest.delete();
        }
    }

    public static void copy(String src, String dest) throws IOException {
        copy(new File(src), new File(dest));
    }

    public static void copy(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new IllegalArgumentException("Src not exist. ");
        } else if (src.isDirectory()) {
            if (!dest.exists() && !mkdirs(dest)) {
                throw new IllegalArgumentException("Make dest dir fail. ");
            }
            LinkedList<File> fileList = new LinkedList<>();
            fileList.add(src);
            while (!fileList.isEmpty()) {
                File[] files = fileList.removeFirst().listFiles();
                if (files == null || files.length == 0) continue;
                for (File file : files) {
                    String subPath = file.toString().replace(src.toString(), "");
                    File destPath = new File(dest, subPath);
                    if (file.isDirectory()) {
                        if (!destPath.exists()) {
                            mkdirs(destPath);
                        }
                        fileList.addFirst(file);
                    } else {
                        copy(file, destPath);
                    }
                }
            }
        } else if (src.isFile()) {
            if (dest.exists() && dest.isFile()) {
                throw new IllegalArgumentException("Dest file already exist. ");
            }
            File destParent = new File(dest.getParent());
            if (!destParent.exists() && !mkdirs(destParent)) {
                throw new IllegalArgumentException("Make dest parent dir fail. ");
            }
            create(dest);
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(src);
                out = new FileOutputStream(dest);
                StreamKit.write(in, out);
            } finally {
                StreamKit.close(in, out);
            }
        }
    }

    public static void move(String src, String dest) throws IOException {
        move(new File(src), new File(dest));
    }

    public static void move(File src, File dest) throws IOException {
        copy(src, dest);
        delete(src);
    }

    public static boolean rename(String src, String dest) {
        return rename(new File(src), new File(dest));
    }

    public static boolean rename(File src, File dest) {
        return src.renameTo(dest);
    }

    public static void write2File(byte[] data, String dest, boolean append) throws IOException {
        write2File(data, new File(dest), append);
    }

    public static void write2File(byte[] data, File dest, boolean append) throws IOException {
        if(data != null && data.length > 0) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(dest, append);
                out.write(data);
                out.flush();
            } finally {
                StreamKit.close(out);
            }
        }
    }

    public static byte[] read2Bytes(String dest) throws IOException {
        return read2Bytes(new File(dest));
    }

    public static byte[] read2Bytes(File dest) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream(dest);
            return StreamKit.read(in);
        } finally {
            StreamKit.close(in);
        }
    }

}
