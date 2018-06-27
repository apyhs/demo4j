package demo.question;

import java.io.PrintStream;

/**
 * 重定向打印流
 */
public class RedirectSysOut {

    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        method(a, b);
        System.out.println(a);
        System.out.println(b);
    }

    private static void method(int a, int b) {
        PrintStream m = new PrintStream(System.out) {
            @Override
            public void println(int x) {
                if(x == 10) super.println(100);
                if(x == 20) super.println(200);
            }
        };
        System.setOut(m);
    }

}
