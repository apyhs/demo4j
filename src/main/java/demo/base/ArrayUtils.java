package demo.base;

public class ArrayUtils {

    public static boolean isEmpty(boolean[] b) {
        return b == null || b.length == 0;
    }

    public static boolean isEmpty(char[] c) {
        return c == null || c.length == 0;
    }

    public static boolean isEmpty(byte[] b) {
        return b == null || b.length == 0;
    }

    public static boolean isEmpty(short[] s) {
        return s == null || s.length == 0;
    }

    public static boolean isEmpty(int[] i) {
        return i == null || i.length == 0;
    }

    public static boolean isEmpty(long[] l) {
        return l == null || l.length == 0;
    }

    public static boolean isEmpty(float[] f) {
        return f == null || f.length == 0;
    }

    public static boolean isEmpty(double[] d) {
        return d == null || d.length == 0;
    }

    public static boolean isEmpty(Object[] o) {
        return o == null || o.length == 0;
    }

    public static boolean isNotEmpty(boolean[] b) {
        return b != null && b.length > 0;
    }

    public static boolean isNotEmpty(char[] c) {
        return c != null && c.length > 0;
    }

    public static boolean isNotEmpty(byte[] b) {
        return b != null && b.length > 0;
    }

    public static boolean isNotEmpty(short[] s) {
        return s != null && s.length > 0;
    }

    public static boolean isNotEmpty(int[] i) {
        return i != null && i.length > 0;
    }

    public static boolean isNotEmpty(long[] l) {
        return l != null && l.length > 0;
    }

    public static boolean isNotEmpty(float[] f) {
        return f != null && f.length > 0;
    }

    public static boolean isNotEmpty(double[] d) {
        return d != null && d.length > 0;
    }

    public static boolean isNotEmpty(Object[] o) {
        return o != null && o.length > 0;
    }

    public static void reverse(boolean[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            boolean temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(char[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            char temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(byte[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            byte temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(short[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            short temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(int[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            int temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(long[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            long temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(float[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            float temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(double[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            double temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

    public static void reverse(Object[] arr) {
        for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
            Object temp = arr[end];
            arr[end] = arr[start];
            arr[start] = temp;
        }
    }

}
