package demo.jdk;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public class RandomKit {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid(String separator) {
        if (separator != null && !StrKit.equals("-", separator)) {
            return UUID.randomUUID().toString().replaceAll("-", separator);
        } else {
            return UUID.randomUUID().toString();
        }
    }

    public static String confuse(String seq) {
        char[] array = seq.toCharArray();
        confuse(array); return new String(array);
    }

    public static void confuse(boolean[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            boolean tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(char[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            char tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(byte[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            byte tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(short[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            short tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(int[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            int tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(long[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            long tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(float[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            float tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(double[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            double tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(Object[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            Object tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static <T> void confuse(List<T> list) {
        for (int i = list.size() - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            T tmp = list.get(nextInt);
            list.set(nextInt, list.get(i));
            list.set(i, tmp);
        }
    }

    public static String desKey() {
        return generateSeq(8);
    }

    public static String desKey(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String desIv() {
        return generateSeq(8);
    }

    public static String desIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String desedeKey() {
        return generateSeq(24);
    }

    public static String desedeKey(char[] chars) {
        return generateSeq(chars, 24);
    }

    public static String desedeIv() {
        return generateSeq(8);
    }

    public static String desedeIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String aesKey() {
        return generateSeq(16);
    }

    public static String aesKey(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String aesIv() {
        return generateSeq(16);
    }

    public static String aesIv(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String blowfishKey() {
        return generateSeq(16);
    }

    public static String blowfishKey(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String blowfishIv() {
        return generateSeq(8);
    }

    public static String blowfishIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static long generateNum(int size) {
        StringBuilder result = new StringBuilder();
        result.append(random.nextInt(9) + 1);
        for (int i = 0, len = size - 1; i < len; i++) {
            result.append(random.nextInt(10));
        }
        return Long.parseLong(result.toString());
    }

    public static String generateSeq(int size) {
        return generateSeq(defaultCharArray, size);
    }

    public static String generateSeq(char[] chars, int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, len = chars.length; i < size; i++)
            result.append(chars[random.nextInt(len)]);
        return result.toString();
    }

    private static final SecureRandom random = new SecureRandom();
    private static char[] defaultCharArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

}
