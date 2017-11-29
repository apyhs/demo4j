package demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * class kit
 */
public class ClassKit {

	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>[] parameterTypes) {
		return getConstructor(clazz, parameterTypes, false);
	}

	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>[] parameterTypes, boolean isPrivate) {
		try {
			Constructor<?> constructor = null;
			if (isPrivate) {
				constructor = clazz.getDeclaredConstructor(parameterTypes);
				constructor.setAccessible(isPrivate);
			} else constructor = clazz.getConstructor(parameterTypes);
			return constructor;
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		return getField(clazz, fieldName, false);
	}

	public static Field getField(Class<?> clazz, String fieldName, boolean isPrivate) {
		try {
			Field result = null;
			if (isPrivate) {
				result = clazz.getDeclaredField(fieldName);
				result.setAccessible(isPrivate);
			} else result = clazz.getField(fieldName);
			return result;
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
		return getMethod(clazz, methodName, parameterTypes, false);
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isPrivate) {
		try {
			Method result = null;
			if (isPrivate) {
				result = clazz.getDeclaredMethod(methodName, parameterTypes);
				result.setAccessible(true);
			} else result = clazz.getMethod(methodName, parameterTypes);
			return result;
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	private static Class<?>[] getParameterTypes(Object... args) {
		int len = args.length;
		Class<?>[] parameterTypes = new Class<?>[len];
		for (int i = 0; i < len; ++i)
			parameterTypes[i] = args[i].getClass();
		return parameterTypes;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz){
		try { return (T) clazz.newInstance(); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz, Object[] args){
		return newInstance(clazz, args, false);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<?> clazz, Object[] args, boolean isPrivate){
		Constructor<?> constructor = getConstructor(clazz, getParameterTypes(args), isPrivate);
		return newInstance(constructor, args);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Constructor<?> constructor, Object[] args) {
		try { return (T) constructor.newInstance(args); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Object getFieldValue(Field field) {
		return getFieldValue(null, field);
	}

	public static Object getFieldValue(Object bean, Field field) {
		try { return field.get(bean); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static void setFieldValue(Field field, Object fieldValue) {
		setFieldValue(null, field, fieldValue);
	}

	public static void setFieldValue(Object bean, Field field, Object fieldValue) {
		try { field.set(bean, fieldValue); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName) {
		return getFieldValue(clazz, fieldName, false);
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName, boolean isPrivate) {
		Field field = getField(clazz, fieldName, isPrivate);
		return getFieldValue(field);
	}

	public static Object getFieldValue(Object bean, String fieldName) {
		return getFieldValue(bean, fieldName, false);
	}

	public static Object getFieldValue(Object bean, String fieldName, boolean isPrivate) {
		Class<?> clazz = bean.getClass();
		Field field = getField(clazz, fieldName, isPrivate);
		return getFieldValue(bean, field);
	}

	public static void setFieldValue(Class<?> clazz, String fieldName, Object fieldValue) {
		setFieldValue(clazz, fieldName, fieldValue, false);
	}

	public static void setFieldValue(Class<?> clazz, String fieldName, Object fieldValue, boolean isPrivate) {
		Field field = getField(clazz, fieldName, isPrivate);
		setFieldValue(field, fieldValue);
	}

	public static void setFieldValue(Object bean, String fieldName, Object fieldValue) {
		setFieldValue(bean, fieldName, fieldValue, false);
	}

	public static void setFieldValue(Object bean, String fieldName, Object fieldValue, boolean isPrivate) {
		Class<?> clazz = bean.getClass();
		Field field = getField(clazz, fieldName, isPrivate);
		setFieldValue(bean, field, fieldValue);
	}

	public static Object invoke(Method method) {
		return invoke(null, method, new Object[]{});
	}

	public static Object invoke(Method method, Object[] args) {
		return invoke(null, method, args);
	}

	public static Object invoke(Object bean, Method method, Object[] args) {
		try { return method.invoke(bean, args); }
		catch (Exception e) { throw new RuntimeException(e); }
	}

	public static Object invoke(Class<?> clazz, String methodName) {
		return invoke(clazz, methodName, new Object[]{}, false);
	}

	public static Object invoke(Class<?> clazz, String methodName, Object[] args) {
		return invoke(clazz, methodName, args, false);
	}

	public static Object invoke(Class<?> clazz, String methodName, Object[] args, boolean isPrivate) {
		Method method = getMethod(clazz, methodName, getParameterTypes(args), isPrivate);
		return invoke(method, args);
	}

	public static Object invoke(Object bean, String methodName) {
		return invoke(bean, methodName, new Object[]{}, false);
	}

	public static Object invoke(Object bean, String methodName, Object[] args) {
		return invoke(bean, methodName, args, false);
	}

	public static Object invoke(Object bean, String methodName, Object[] args, boolean isPrivate) {
		Class<?> clazz = bean.getClass();
		Method method = getMethod(clazz, methodName, getParameterTypes(args), isPrivate);
		return invoke(bean, method, args);
	}

}
