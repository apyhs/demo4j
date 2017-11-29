package demo.base;

import java.io.IOException;
import java.io.Serializable;

public class ObjUtils {

    public static <T> T val(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T simpleClone(T obj) throws ReflectiveOperationException {
        return (T) ClassUtils.invoke(obj, "clone");
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepClone(T obj) throws IOException, ClassNotFoundException {
        byte[] data = StreamUtils.serialize(obj);
        return (T) StreamUtils.deserialize(data);
    }

}
