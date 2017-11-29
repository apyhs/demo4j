package demo.jdk;

import java.util.Collection;
import java.util.List;

public class ArrayKit {

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

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean notEmpty(boolean[] b) {
        return b != null && b.length > 0;
    }

    public static boolean notEmpty(char[] c) {
        return c != null && c.length > 0;
    }

    public static boolean notEmpty(byte[] b) {
        return b != null && b.length > 0;
    }

    public static boolean notEmpty(short[] s) {
        return s != null && s.length > 0;
    }

    public static boolean notEmpty(int[] i) {
        return i != null && i.length > 0;
    }

    public static boolean notEmpty(long[] l) {
        return l != null && l.length > 0;
    }

    public static boolean notEmpty(float[] f) {
        return f != null && f.length > 0;
    }

    public static boolean notEmpty(double[] d) {
        return d != null && d.length > 0;
    }

    public static boolean notEmpty(Object[] o) {
        return o != null && o.length > 0;
    }

    public static boolean notEmpty(Collection c) {
        return c != null && !c.isEmpty();
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

    public static <T extends Comparable<? super T>> void reverse(List<T> list) {
        for (int start = 0, end = list.size() - 1; start < end; start++, end--) {
            T temp = list.get(end);
            list.set(end, list.get(start));
            list.set(start, temp);
        }
    }

}
