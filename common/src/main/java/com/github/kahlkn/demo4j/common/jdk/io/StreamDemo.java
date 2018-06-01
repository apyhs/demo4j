package com.github.kahlkn.demo4j.common.jdk.io;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamDemo {

    /*mark就像书签一样，在这个BufferedReader对应的buffer里作个标记，以后再调用reset时就可以再回到这个mark过的地方。
    mark方法有个参数，通过这个整型参数，你告诉系统，希望在读出这么多个字符之前，这个mark保持有效。
    读过这么多字符之后，系统可以使mark不再有效，而你不能觉得奇怪或怪罪它。
    这跟buffer有关，如果你需要很长的距离，那么系统就必须分配很大的buffer来保持你的mark。
    // eg.
    // reader is a BufferedReader

    reader.mark(50);//要求在50个字符之内，这个mark应该保持有效，系统会保证buffer至少可以存储50个字符
    int a = reader.read();//读了一个字符
    int b = reader.read();//又读了一个字符

    //做了某些处理，发现需要再读一次
    reader.reset();
    reader.read();//读到的字符和a相同
    reader.read();//读到的字符和b相同*/

    @Test
    public void test1() throws Exception {
        byte[] buffer = new byte[8192];
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("C:\\Users\\admin\\Desktop\\分析.txt"));
        System.out.println(in.markSupported());
        in.mark(8192);
        int len = in.read(buffer);
        System.out.println(len);
        System.out.println(new String(buffer));
        in.reset();
        in.read(buffer);
        System.out.println(new String(buffer));
    }

    public static <T extends InputStream> byte[] readNotAffect(T in, int len) {
        try {
            if (!in.markSupported()) {
                throw new IOException("mark/reset not supported");
            } byte[] result = new byte[len];
            in.mark(len); in.read(result);
            in.reset(); return result;
        } catch (Exception e) { throw new RuntimeException(e); }
    }

}
