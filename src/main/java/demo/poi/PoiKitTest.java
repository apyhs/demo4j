package demo.poi;

import java.util.ArrayList;
import java.util.List;

import demo.pojo.User1;
import org.junit.Test;

import kit4j.poi.Field;
import kit4j.poi.PoiKit;

public class PoiKitTest {
	private List<Field> fields = Field.convert(new Object[][]{{"name", String.class, "姓名"}
			, {"pass", String.class, "密码"}, {"gender", Boolean.class, "性别"}, {"age", Integer.class, "年龄"}});
	
	@Test
	public void newExcel(){
		List<User1> users = new ArrayList<>();
		users.add(new User1("张三", "123456", true, 16));
		users.add(new User1("李四", "123abc", true, 20));
		users.add(new User1("王五", "abc456", false, 19));
		users.add(new User1("赵六", "efg456", true, 15));
		users.add(new User1("李七", "abcdef", false, 18));
		PoiKit.newExcel(fields, users, "d:\\aa.xlsx");
	}
	
	@Test
	public void readExcel(){
		List<User1> list = PoiKit.readExcel(fields, "d:\\aa.xlsx", User1.class);
		for (User1 user : list) {
			System.out.println(user);
		}
	}
}
