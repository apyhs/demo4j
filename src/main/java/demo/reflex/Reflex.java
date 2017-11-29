package demo.reflex;

public class Reflex<T> {
	private Class<T> clazz;
	private T currentInstance;
	
	public T getInstance(){
		return currentInstance;
	}
	
	public void setInstance(T t){
		currentInstance = t;
	}

	@SuppressWarnings("unchecked")
	public Reflex(T t) {
		super();
		clazz = (Class<T>) t.getClass();
		currentInstance = t;
	}
	
	public Reflex(Class<T> c) {
		super();
		clazz = c;
	}
	
	public T newInstance(){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object invoke(String methodName, Object... args){
		return invoke(currentInstance, methodName, args);
	}
	
	private Object invoke(Object callObject, String methodName, Object... args){
		try {
			Class<?>[] parameterTypes = new Class<?>[args.length];
			for (int i = 0; i < args.length; ++i) {
				parameterTypes[i] = args[i].getClass();
			}
			return clazz.getMethod(methodName, parameterTypes).invoke(callObject, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
