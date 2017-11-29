package demo.kit;

import java.lang.ref.SoftReference;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import kit4j.StrKit;

public class TempKit2 {
	private static final ConcurrentHashMap<String, Temp> map = new ConcurrentHashMap<>();
	private static final String rootTemp = "root";
	private static final int defaultSize = 1048576;
	
	static {
		create(rootTemp, defaultSize);
	}
	
	public static Temp create(String tempName, int tempSize){
		if(rootTemp.equals(tempName) && use() != null){
			return use();
		}
		Temp temp = new Temp(tempName, tempSize);
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
		return create(rootTemp, defaultSize);
	}
	
	public static class Temp{
		private final ConcurrentHashMap<Object, SoftReference<Object>> map = new ConcurrentHashMap<>();
		private final ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
		private String name;
		private int size;
		
		private void check(){
			int now = map.size();
			int count = now - size;
			if(count >= 0){
				count += size / 2;
				for (int i = 0; i <= count; i++) {
					map.remove(queue.poll());
				}
			}
		}
		
		public Temp(String name, int size) {
			if(StrKit.isBlank(name))
				throw new IllegalArgumentException("name is blank!");
			if(size < 128)
				throw new IllegalArgumentException("size is too short, must greater than 128!");
			this.name = name;
			this.size = size;
		}

		public String getName() {
			return name;
		}

		public void put(Object key, Object value){
			check(); queue.add(key);
			map.put(key, new SoftReference<Object>(value));
		}
		
		public Object get(Object key){
			return map.get(key).get();
		}
		
		public void remove(Object key){
			SoftReference<Object> sf = map.remove(key);
			if(sf != null) sf.clear();
		}
		
		public void clear(){
			Set<Object> keys = map.keySet();
			for (Object key : keys) {
				SoftReference<Object> sf = map.get(key);
				if(sf != null) sf.clear();
			}
			map.clear();
		}
	}
}
