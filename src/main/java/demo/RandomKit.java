package demo;

import java.security.SecureRandom;
import java.util.List;

public class RandomKit {
    private static final SecureRandom random = new SecureRandom();
    private static char[] defaultCharArray = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static char[] numCharArray = "0123456789".toCharArray();

    /**
     * char array using generate random sequence
     */
    public static void setDefCharArray(char[] defCharArray){
        if (defCharArray == null || defCharArray.length == 0) {
            throw new IllegalArgumentException("default char array can not be blank!");
        } defaultCharArray = defCharArray;
    }

    public static String sort(String seq) {
        StringBuilder result = new StringBuilder();
        char[] array = seq.toCharArray();
        for (int i = array.length - 1; i > 0; --i) {
            int nextInt = random.nextInt(i);
            result.append(array[nextInt]);
            char tmp = array[nextInt];
            array[nextInt] = array[i];
            array[i] = tmp;
        } result.append(array[0]);
        return result.toString();
    }

    public static void sort(Object[] arr) {
        for (int i = arr.length - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            Object tmp = arr[nextInt];
            arr[nextInt] = arr[i];
            arr[i] = tmp;
        }
    }

    @SuppressWarnings("unchecked")
    public static void sort(List list) {
        for (int i = list.size() - 1; i > 1; --i) {
            int nextInt = random.nextInt(i);
            Object tmp = list.get(nextInt);
            list.set(nextInt, list.get(i));
            list.set(i, tmp);
        }
    }

    public static String desKey() {
        return generateSeq(8);
    }

    public static String desIv() {
        return generateSeq(8);
    }

    public static String desedeKey() {
        return generateSeq(24);
    }

    public static String desedeIv() {
        return generateSeq(8);
    }

    public static String aesKey() {
        return generateSeq(16);
    }

    public static String aesIv() {
        return generateSeq(16);
    }

    public static String blowfishKey() {
        return generateSeq(16);
    }

    public static String blowfishIv() {
        return generateSeq(8);
    }

    public static String generateNum(int size) {
        return generateSeq(numCharArray, size);
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

}
