package demo.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassUtils {

	public static Object newInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException {
		return clazz.newInstance();
	}

	public static Object newInstance(Class<?> clazz, Object[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		return newInstance(clazz, args, true);
	}

	public static Object newInstance(Class<?> clazz, Object[] args, boolean isPublic) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<?> constructor = isPublic ?
				getConstructor(clazz, getParameterTypes(args)) :
				getDeclaredConstructor(clazz, getParameterTypes(args));
		return constructor.newInstance(args);
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		return getFieldValue(clazz, fieldName, true);
	}

	public static Object getFieldValue(Class<?> clazz, String fieldName, boolean isPublic) throws IllegalAccessException, NoSuchFieldException {
		Field field = isPublic ?
				getField(clazz, fieldName) :
				getDeclaredField(clazz, fieldName);
		return field.get(null);
	}

	public static Object getFieldValue(Object bean, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		return getFieldValue(bean, fieldName, true);
	}

	public static Object getFieldValue(Object bean, String fieldName, boolean isPublic) throws NoSuchFieldException, IllegalAccessException {
		Class<?> clazz = bean.getClass();
		Field field = isPublic ?
				getField(clazz, fieldName) :
				getDeclaredField(clazz, fieldName);
		return field.get(bean);
	}

	public static void setFieldValue(Class<?> clazz, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
		setFieldValue(clazz, fieldName, fieldValue, true);
	}

	public static void setFieldValue(Class<?> clazz, String fieldName, Object fieldValue, boolean isPublic) throws IllegalAccessException, NoSuchFieldException {
		Field field = isPublic ?
				getField(clazz, fieldName) :
				getDeclaredField(clazz, fieldName);
		field.set(null, fieldValue);
	}

	public static void setFieldValue(Object bean, String fieldName, Object fieldValue) throws NoSuchFieldException, IllegalAccessException {
		setFieldValue(bean, fieldName, fieldValue, false);
	}

	public static void setFieldValue(Object bean, String fieldName, Object fieldValue, boolean isPublic) throws IllegalAccessException, NoSuchFieldException {
		Class<?> clazz = bean.getClass();
		Field field = isPublic ?
				getField(clazz, fieldName) :
				getDeclaredField(clazz, fieldName);
		field.set(bean, fieldValue);
	}

	public static Object getter(Object bean, String fieldName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		fieldName = STRING_GET + StrUtils.firstCharToUpperCase(fieldName);
		return invoke(bean, fieldName, new Object[]{}, true);
	}

	public static Object setter(Object bean, String fieldName, Object value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		fieldName = STRING_SET + StrUtils.firstCharToUpperCase(fieldName);
		return invoke(bean, fieldName, new Object[]{value}, true);
	}

	public static Object invoke(Class<?> clazz, String methodName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return invoke(clazz, methodName, new Object[]{}, true);
	}

	public static Object invoke(Class<?> clazz, String methodName, Object[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		return invoke(clazz, methodName, args, true);
	}

	public static Object invoke(Class<?> clazz, String methodName, Object[] args, boolean isPublic) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
		Method method = isPublic ?
				getMethod(clazz, methodName, getParameterTypes(args)) :
				getDeclaredMethod(clazz, methodName, getParameterTypes(args));
		return method.invoke(null, args);
	}

	public static Object invoke(Object bean, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invoke(bean, methodName, new Object[]{}, true);
	}

	public static Object invoke(Object bean, String methodName, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invoke(bean, methodName, args, true);
	}

	public static Object invoke(Object bean, String methodName, Object[] args, boolean isPublic) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> clazz = bean.getClass();
		Method method = isPublic ?
				getMethod(clazz, methodName, getParameterTypes(args)) :
				getDeclaredMethod(clazz, methodName, getParameterTypes(args));
		return method.invoke(bean, args);
	}

	public static Object newInstance(Constructor<?> constructor, Object[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
		return constructor.newInstance(args);
	}

	public static Object getFieldValue(Field field) throws IllegalAccessException {
		return getFieldValue(null, field);
	}

	public static Object getFieldValue(Object bean, Field field) throws IllegalAccessException {
		return field.get(bean);
	}

	public static void setFieldValue(Field field, Object fieldValue) throws IllegalAccessException {
		setFieldValue(null, field, fieldValue);
	}

	public static void setFieldValue(Object bean, Field field, Object fieldValue) throws IllegalAccessException {
		field.set(bean, fieldValue);
	}

	public static Object invoke(Method method) throws InvocationTargetException, IllegalAccessException {
		return invoke(null, method, new Object[]{});
	}

	public static Object invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
		return invoke(null, method, args);
	}

	public static Object invoke(Object bean, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
		return method.invoke(bean, args);
	}

	public static Constructor<?> getConstructor(Class<?> clazz, Class<?>[] parameterTypes) throws NoSuchMethodException {
		return clazz.getConstructor(parameterTypes);
	}

	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		return clazz.getField(fieldName);
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		return clazz.getMethod(methodName, parameterTypes);
	}

	public static Constructor<?> getDeclaredConstructor(Class<?> clazz, Class<?>[] parameterTypes) throws NoSuchMethodException {
		Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
		constructor.setAccessible(true);
		return constructor;
	}

	public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		Field result = clazz.getDeclaredField(fieldName);
		result.setAccessible(true);
		return result;
	}

	public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
		Method result = clazz.getDeclaredMethod(methodName, parameterTypes);
		result.setAccessible(true);
		return result;
	}

	private static Class<?>[] getParameterTypes(Object... args) {
		int len = args.length;
		Class<?>[] parameterTypes = new Class<?>[len];
		for (int i = 0; i < len; ++i)
			parameterTypes[i] = args[i].getClass();
		return parameterTypes;
	}

	public static final String STRING_GET = "get";
	public static final String STRING_SET = "set";

}
