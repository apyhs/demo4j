package demo.question.tempkit;

import java.lang.ref.SoftReference;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TempKit1 {
	private static final ConcurrentHashMap<String, Temp> map = new ConcurrentHashMap<>();
	private static final String rootTemp = "root";
	
	static {
		create(rootTemp);
	}
	
	public static Temp create(String tempName){
		if(rootTemp.equals(tempName) && use() != null){
			return use();
		}
		Temp temp = new Temp(tempName);
		map.put(tempName, temp);
		return temp;
	}
	
	public static Temp use(){
		return use(rootTemp);
	}
	
	public static Temp use(String tempName){
		return map.get(tempName);
	}
	
	public static Temp clear(){
		Set<String> keys = map.keySet();
		for (String key : keys) {
			map.get(key).clear();
		}
		map.clear();
		return create(rootTemp);
	}
	
	public static class Temp{
		private final ConcurrentHashMap<Object, SoftReference<Object>> map = new ConcurrentHashMap<>();
		private String name;
		
		public Temp(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void put(Object key, Object value){
			map.put(key, new SoftReference<>(value));
		}
		
		public Object get(Object key){
			return map.get(key).get();
		}
		
		public void clear(){
			Set<Object> keys = map.keySet();
			for (Object key : keys) {
				SoftReference<Object> sf = map.get(key);
				if(sf != null){
					sf.clear();
				}
			}
			map.clear();
		}
	}
}
