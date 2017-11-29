package demo.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }

    public static boolean isNotEmpty(Collection coll) {
        return (coll != null && !coll.isEmpty());
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map map) {
        return (map != null && !map.isEmpty());
    }

    public static <T> void reverse(List<T> list) {
        for (int start = 0, end = list.size() - 1; start < end; start++, end--) {
            T temp = list.get(end);
            list.set(end, list.get(start));
            list.set(start, temp);
        }
    }

    public static Map beanToMap(Object obj) throws InvocationTargetException, IllegalAccessException {
        Map<Object, Object> result = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (method.isAccessible() &&
                    name.length() > 3 &&
                    name.indexOf(ClassUtils.STRING_GET) == 0) {
                name = name.substring(3, name.length());
                name = StrUtils.firstCharToLowerCase(name);
                result.put(name, method.invoke(obj));
            }
        }
        return result;
    }

    public static <T> List<Map> beansToMaps(List<T> list) throws InvocationTargetException, IllegalAccessException {
        List<Map> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (T t : list) {
                result.add(beanToMap(t));
            }
        }
        return result;
    }

    public static <T> T mapToBean(Map<?, ?> map, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T bean = clazz.newInstance();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String name = entry.getKey() + "";
            if (StrUtils.isBlank(name)) continue;
            Object value = entry.getValue();
            name = ClassUtils.STRING_SET + StrUtils.firstCharToUpperCase(name);
            Method method = clazz.getMethod(name, value.getClass());
            if (!method.isAccessible()) continue;
            method.invoke(bean, value);
        }
        return bean;
    }

    public static <T> List<T> mapsToBeans(List<Map> list, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        List<T> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map map : list) {
                result.add(mapToBean(map, clazz));
            }
        }
        return result;
    }

}
