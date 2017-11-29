package demo.base;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

public class RandomUtils {

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid(String separator) {
        if (separator != null && !StrUtils.equals("-", separator)) {
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
            int nextInt = RANDOM.nextInt(i);
            boolean tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(char[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            char tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(byte[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            byte tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(short[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            short tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(int[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            int tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(long[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            long tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(float[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            float tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(double[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            double tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void confuse(Object[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            Object tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    public static <T> void confuse(List<T> list) {
        for (int i = list.size() - 1; i > 1; --i) {
            int nextInt = RANDOM.nextInt(i);
            T tmp = list.get(nextInt);
            list.set(nextInt, list.get(i));
            list.set(i, tmp);
        }
    }

    public static String generateDesKey() {
        return generateSeq(8);
    }

    public static String generateDesKey(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String generateDesIv() {
        return generateSeq(8);
    }

    public static String generateDesIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String generateDesedeKey() {
        return generateSeq(24);
    }

    public static String generateDesedeKey(char[] chars) {
        return generateSeq(chars, 24);
    }

    public static String generateDesedeIv() {
        return generateSeq(8);
    }

    public static String generateDesedeIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static String generateAesKey() {
        return generateSeq(16);
    }

    public static String generateAesKey(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String generateAesIv() {
        return generateSeq(16);
    }

    public static String generateAesIv(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String generateBlowfishKey() {
        return generateSeq(16);
    }

    public static String generateBlowfishKey(char[] chars) {
        return generateSeq(chars, 16);
    }

    public static String generateBlowfishIv() {
        return generateSeq(8);
    }

    public static String generateBlowfishIv(char[] chars) {
        return generateSeq(chars, 8);
    }

    public static long generateNum(int length) {
        StringBuilder result = new StringBuilder();
        result.append(RANDOM.nextInt(9) + 1);
        for (int i = 0, len = length - 1; i < len; i++) {
            result.append(RANDOM.nextInt(10));
        }
        return Long.parseLong(result.toString());
    }

    public static String generateSeq(int size) {
        return generateSeq(DEFAULT_CHAR_ARRAY, size);
    }

    public static String generateSeq(char[] chars, int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0, len = chars.length; i < size; i++)
            result.append(chars[RANDOM.nextInt(len)]);
        return result.toString();
    }

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] DEFAULT_CHAR_ARRAY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

}
