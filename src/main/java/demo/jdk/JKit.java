package demo.jdk;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JKit {

    public static <T> T val(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean notEmpty(Object o) {
        return o != null;
    }

    public static boolean match(String regExp, CharSequence input) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean match(Pattern pattern, CharSequence input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T simpleClone(T obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return (T) ClassKit.invoke(obj, "clone");
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        byte[] data = StreamKit.serialize(obj);
        return (T) StreamKit.deserialize(data);
    }

}
