package demo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

public class ObjKit {

    public static <T> T val(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static Map<String, Object> beanToMap(Object obj) {
        try {
            Map<String, Object> result = new HashMap<>();
            Class<?> clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                String name = method.getName();
                if (method.isAccessible() &&
                        name.length() > 3 &&
                        name.indexOf("get") == 0) {
                    name = name.substring(3, name.length());
                    name = StrKit.firstChToLower(name);
                    result.put(name, method.invoke(obj));
                }
            } return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String name = entry.getKey();
                if (StrKit.isBlank(name)) continue;
                Object value = entry.getValue();
                name = "set" + StrKit.firstChToUpper(name);
                Method method = clazz.getMethod(name, value.getClass());
                if (!method.isAccessible()) continue;
                method.invoke(bean, value);
            } return bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T simpleClone(T obj) {
        return (T) ClassKit.invoke(obj, "clone");
    }

    public static <T extends Serializable> T deepClone(T obj) {
        byte[] data = StreamKit.serializeObject(obj);
        return StreamKit.deserializeObject(data);
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid(String separator) {
        if (separator != null && !StrKit.equals("-", separator)) {
            return UUID.randomUUID().toString().replaceAll("-", separator);
        } else { return uuid(); }
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(Number n) {
        return n == null || n.equals(0);
    }

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

    public static void ascSort(char[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(byte[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(short[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(int[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(long[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(float[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(double[] arr) {
        Arrays.sort(arr);
    }

    public static void ascSort(Object[] arr) {
        Arrays.sort(arr);
    }

    public static <T extends Comparable<? super T>> void ascSort(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void ascSort(List<T> list, Comparator<? super T> c) {
        Collections.sort(list, c);
    }

    public static void descSort(char[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(byte[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(short[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(int[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(long[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(float[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(double[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void descSort(Object[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static <T extends Comparable<? super T>> void descSort(List<T> list) {
        Collections.sort(list);
        reverse(list);
    }

    public static <T extends Comparable<? super T>> void descSort(List<T> list, Comparator<? super T> c) {
        Collections.sort(list, c);
        reverse(list);
    }

}
