package demo.cache;

public class StaticStuff {
    static int x = 10;

    static {
        System.out.println("5 x = " + x);
        x *= 5;
    }

    public static void main(String[] args) {
        System.out.println("x = " + x);
    }

    static {
        System.out.println("3 x = " + x);
        x /= 3;
    }

}
