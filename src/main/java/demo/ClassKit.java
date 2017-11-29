package demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassKit {

	/**
	 * get args types
	 */
	private static Class<?>[] getParameterTypes(Object... args) {
		int len = args.length;
		Class<?>[] parameterTypes = new Class<?>[len];
		for (int i = 0; i < len; ++i)
			parameterTypes[i] = args[i].getClass();
		return parameterTypes;
	}

	public static Object newInstance(Class<?> clazz){
		return newInstance(clazz, false);
	}

	public static Object newInstance(Class<?> clazz, boolean isPrivate, Object... args){
		return newInstance(clazz, isPrivate, getParameterTypes(args), args);
	}

	public static Object newInstance(Class<?> clazz, boolean isPrivate, Class<?>[] parameterTypes, Object[] args) {
		try {
			Constructor<?> constructor = null;
			if (isPrivate) {
				constructor = clazz.getDeclaredConstructor(parameterTypes);
				constructor.setAccessible(isPrivate);
			} else {
				constructor = clazz.getConstructor(parameterTypes);
			}
			return constructor.newInstance(args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object get(Object obj, String fieldName, boolean isPrivate) {
		return get(obj.getClass(), fieldName, isPrivate, obj);
	}

	public static Object get(Class<?> clazz, String fieldName, boolean isPrivate) {
		return get(clazz, fieldName, isPrivate, null);
	}

	public static Object get(Class<?> clazz, String fieldName, boolean isPrivate, Object obj) {
		try {
			Field field = null;
			if (isPrivate) {
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(isPrivate);
			} else {
				field = clazz.getField(fieldName);
			} return field.get(field);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void set(Object obj, String fieldName, boolean isPrivate, Object fieldValue) {
		set(obj.getClass(), fieldName, isPrivate, obj, fieldValue);
	}

	public static void set(Class<?> clazz, String fieldName, boolean isPrivate, Object fieldValue) {
		set(clazz, fieldName, isPrivate, null, fieldValue);
	}

	public static void set(Class<?> clazz, String fieldName, boolean isPrivate, Object obj, Object fieldValue) {
		try {
			Field field = null;
			if (isPrivate) {
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(isPrivate);
			} else {
				field = clazz.getField(fieldName);
			} field.set(obj, fieldValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object getter(Object bean, String fieldName) {
		return invoke(bean, "get" + StrKit.firstChToUpper(fieldName), false);
	}

	public static void setter(Object bean, String fieldName, Object fieldValue) {
		invoke(bean, "set" + StrKit.firstChToUpper(fieldName), false, fieldValue);
	}

	public static Object invoke(Object obj, String methodName, boolean isPrivate, Object... args) {
		return invoke(obj.getClass(), methodName, getParameterTypes(args), isPrivate, obj, args);
	}

	public static Object invoke(Class<?> clazz, String methodName, boolean isPrivate, Object... args) {
		return invoke(clazz, methodName, getParameterTypes(args), isPrivate, null, args);
	}

	public static Object invoke(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isPrivate, Object obj, Object[] args) {
		try {
			Method method = null;
			if (isPrivate) {
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
				method.setAccessible(true);
			} else {
				method = clazz.getMethod(methodName, parameterTypes);
			} return method.invoke(obj, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
