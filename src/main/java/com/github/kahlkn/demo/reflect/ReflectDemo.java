package com.github.kahlkn.demo.reflect;

import com.apyhs.artoria.reflect.ReflectUtils;
import com.apyhs.artoria.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectDemo {

    public static Map<String, Field> findFields(Class<?> clazz) throws NoSuchFieldException {
        Assert.notNull(clazz, "Clazz must is not null. ");
        // TODO: has some problem
        Map<String, Field> result = new LinkedHashMap<String, Field>();
        Class<?> inputClazz = clazz;
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                boolean b = inputClazz != clazz;
                int mod = field.getModifiers();
                boolean isStatic = Modifier.isStatic(mod);
                if (b ^ isStatic) {
                    String name = field.getName();
                    if (!result.containsKey(name)) {
                        Field fd = ReflectUtils.findField(clazz, name);
                        result.put(name, fd);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return result;
    }

}
