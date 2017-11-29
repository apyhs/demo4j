package demo.jdk.util;

import demo.jdk.ArrayKit;
import demo.jdk.ClassKit;
import demo.jdk.StrKit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JMap extends HashMap<Object, Object> {
    private static final String STRING_CODE = "code";
    private static final String STRING_DATA = "data";
    private static final String STRING_MSG = "msg";
    private static final String STRING_DEBUG = "debug";

    public JMap set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public JMap set(Map map) {
        super.putAll(map);
        return this;
    }

    public JMap set(JMap map) {
        super.putAll(map);
        return this;
    }

    public JMap setCode(Object code) {
        super.put(STRING_CODE, code);
        return this;
    }

    public JMap setData(Object data) {
        super.put(STRING_DATA, data);
        return this;
    }

    public JMap setMsg(Object msg) {
        super.put(STRING_MSG, msg);
        return this;
    }

    public JMap setDebug(Object debug) {
        super.put(STRING_DEBUG, debug);
        return this;
    }

    public static JMap create() {
        return new JMap();
    }

    public static JMap create(JMap map) {
        return new JMap().set(map);
    }

    public static JMap create(Object key, Object value) {
        return new JMap().set(key, value);
    }

    public static JMap create(Object obj) throws InvocationTargetException, IllegalAccessException {
        JMap result = JMap.create();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (method.isAccessible() &&
                    name.length() > 3 &&
                    name.indexOf(ClassKit.STRING_GET) == 0) {
                name = name.substring(3, name.length());
                name = StrKit.firstCharToLowerCase(name);
                result.put(name, method.invoke(obj));
            }
        }
        return result;
    }

    public static <T> List<JMap> create(List<T> list) throws InvocationTargetException, IllegalAccessException {
        List<JMap> result = new ArrayList<>();
        if (ArrayKit.notEmpty(list)) {
            for (T t : list) {
                result.add(create(t));
            }
        }
        return result;
    }

    public static <T> T parse(JMap map, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T bean = clazz.newInstance();
        for (Entry<Object, Object> entry : map.entrySet()) {
            String name = entry.getKey() + "";
            if (StrKit.isBlank(name)) continue;
            Object value = entry.getValue();
            name = ClassKit.STRING_SET + StrKit.firstCharToUpperCase(name);
            Method method = clazz.getMethod(name, value.getClass());
            if (!method.isAccessible()) continue;
            method.invoke(bean, value);
        }
        return bean;
    }

    public static <T> List<T> parse(List<JMap> list, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<T> result = new ArrayList<>();
        if (ArrayKit.notEmpty(list)) {
            for (JMap map : list) {
                result.add(parse(map, clazz));
            }
        }
        return result;
    }

}
