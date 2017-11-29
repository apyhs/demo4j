package demo.poi;

import demo.ClassKit;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * excel sheet field
 */
public class Field {
	
	private String name;
	private Class<?> type;
	private String text;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Field() {
		super();
	}

	public Field(String name, Class<?> type, String text) {
		super();
		this.name = name;
		this.type = type;
		this.text = text;
	}

	/**
	 * get fields text
	 */
	public static List<String> getTexts(List<Field> fields){
		List<String> result = new ArrayList<>();
		if(fields != null && fields.size() > 0){
			for (Field field : fields) {
				result.add(field.getText());
			}
		}
		return result;
	}
	
	/**
	 * parse bean to list by fields
	 */
	public static <T> List<Object> parse(List<Field> fields, T bean) 
			throws ReflectiveOperationException {
		List<Object> result = new ArrayList<>();
		if(fields != null && fields.size() > 0){
			for (Field field : fields) {
				result.add(ClassKit.getter(bean, field.getName()));
			}
		}
		return result;
	}
	
	/**
	 * make data to bean who class is clazz
	 */
	public static <T> T make(List<Field> fields, List<?> data, Class<T> clazz) 
			throws ReflectiveOperationException {
		T result = clazz.newInstance();
		int len = data.size();
		for (int i = 0; i < len; i++) {
			Object fieldValue = ConvertUtils.convert(data.get(i), fields.get(i).getType());
			ClassKit.setter(result, fields.get(i).getName(), fieldValue);
		}
		return result;
	}
	
	public static List<Field> convert(Object[][] fields) {
		List<Field> result = new ArrayList<>();
		try {
			if (fields != null && fields.length > 0) {
				for (Object[] field : fields) {
					result.add(new Field((String)field[0]
							, (Class<?>)field[1], (String)field[2]));
				}
			}
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
}
